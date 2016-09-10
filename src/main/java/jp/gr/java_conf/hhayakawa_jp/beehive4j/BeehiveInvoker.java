package jp.gr.java_conf.hhayakawa_jp.beehive4j;

import java.net.HttpCookie;
import java.net.HttpRetryException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jp.gr.java_conf.hhayakawa_jp.beehive4j.exception.BeehiveApiFaultException;
import jp.gr.java_conf.hhayakawa_jp.beehive4j.exception.BeehiveHttpClientErrorException;
import jp.gr.java_conf.hhayakawa_jp.beehive4j.exception.BeehiveHttpErrorException;
import jp.gr.java_conf.hhayakawa_jp.beehive4j.exception.BeehiveHttpServerErrorException;
import jp.gr.java_conf.hhayakawa_jp.beehive4j.exception.BeehiveUnexpectedFailureException;

public abstract class BeehiveInvoker<T> {

    private final String apiRoot;

    private HttpHeaders headers = new HttpHeaders();

    private String pathValue = "";

    private Map<String, String> urlQueries = new HashMap<String, String>();

    private T requestPayload;

    private static final RestTemplate template = new RestTemplate();

    static {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        SimpleModule module = new SimpleModule();
        module.addSerializer(
                ZonedDateTime.class, new ZonedDateTimeSerializer());
        module.addDeserializer(
                BeehiveResponse.class, new BeehiveResponseDeserializer());
        mapper.registerModule(module);

        // converter
        MappingJackson2HttpMessageConverter converter =
                new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(mapper);
        List<HttpMessageConverter<?>> converters =
                new ArrayList<HttpMessageConverter<?>>(1);
        converters.add(converter);
        template.setMessageConverters(converters);

        // error handler
        template.setErrorHandler(new BypassHttpErrorErrorHandler());

        // intercepter
        List<ClientHttpRequestInterceptor> interceptors = 
                new ArrayList<ClientHttpRequestInterceptor>(1);
        interceptors.add(new BeehiveRequestLoggingInterceptor());
        template.setInterceptors(interceptors);;
    }

    public BeehiveInvoker(String apiRoot, BeehiveCredential credential) {
        this.apiRoot = apiRoot;
        setDefaultHeaders(credential);
        setDefaultUrlQueries(credential);
    }

    public ResponseEntity<BeehiveResponse> invoke()
            throws BeehiveApiFaultException {
        HttpEntity<T> entity = new HttpEntity<T>(requestPayload, headers);
        ResponseEntity<BeehiveResponse> result = null;
        try {
            result = template.exchange(makeUrlString() + makeQueryString(),
                    getHttpMethod(), entity, BeehiveResponse.class);
        } catch (RestClientException e) {
            Throwable cause = e.getCause();
            if (cause != null && cause instanceof HttpRetryException) {
                HttpRetryException re = (HttpRetryException) cause;
                if (HttpStatus.UNAUTHORIZED.value() == re.responseCode()) {
                    throw new BeehiveHttpClientErrorException(
                            null, HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                            null, HttpStatus.UNAUTHORIZED);
                }
            }
            throw new BeehiveUnexpectedFailureException("unexpected filure.", e);
        }
        BeehiveResponse response = result.getBody();
        if (response != null && "restFault".equals(response.getBeeType())) {
            throwExceptionIfNecessary(result);
        }
        return result;
    }

    public void addHeader(Map<String, String> headers) {
        this.headers.setAll(headers);
    }

    public void addQuery(Map<String, String> queries) {
        this.urlQueries.putAll(queries);
    }

    public void setPathValue(String value) {
        this.pathValue = value;
    }

    public void setRequestPayload(T requestPayload) {
        this.requestPayload = requestPayload;
    }

    protected T getPreparedRequestPayload() {
        return this.requestPayload;
    }

    abstract String getApiPath();

    abstract HttpMethod getHttpMethod();

    private void setDefaultHeaders(BeehiveCredential credential) {
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (credential == null) {
            return;
        }
        HttpCookie sessionCookie = credential.getSession();
        headers.add(HttpHeaders.COOKIE,
                sessionCookie.getName() + "=" + sessionCookie.getValue());
    }

    private void setDefaultUrlQueries(BeehiveCredential credential) {
        if (credential == null) {
            return;
        }
        Map<String, String> csrf = new HashMap<String, String>(1);
        csrf.put("anticsrf", credential.getAnticsrf());
        addQuery(csrf);
    }

    private String getPathValue() {
        return this.pathValue;
    }

    private String makeUrlString() {
        StringBuffer bf = new StringBuffer();
        bf.append(apiRoot);
        bf.append(getApiPath());
        String pathValue = getPathValue();
        if (pathValue.length() > 0) {
            bf.append("/");
            bf.append(pathValue);
        }
        return bf.toString();
    }

    private String makeQueryString() {
        if (urlQueries == null || urlQueries.isEmpty()) {
            return "";
        }
        StringBuffer bf = new StringBuffer("?");
        Set<Entry<String, String>> entries = urlQueries.entrySet();
        int i = 0;
        for (Entry<String, String> entry : entries) {
            if (++i > 0) {
                bf.append("&");
            }
            bf.append(entry.getKey() + "=" + entry.getValue());
        }
        return bf.toString();
    }

    private static void throwExceptionIfNecessary(
            ResponseEntity<BeehiveResponse> responseEntity)
                    throws BeehiveHttpErrorException {
        JsonNode json = responseEntity.getBody().getJson();
        JsonNode fault = json.get("fault");
        String action = fault.get("action").asText();
        String cause = fault.get("cause").asText();
        String effect = fault.get("effect").asText();
        HttpStatus httpStatus = responseEntity.getStatusCode();
        if (httpStatus.is4xxClientError()) {
            throw new BeehiveHttpClientErrorException(
                    action, cause, effect, httpStatus);
        } else if (httpStatus.is5xxServerError()) {
            throw new BeehiveHttpServerErrorException(
                    action, cause, effect, httpStatus);
        }
    }

}
