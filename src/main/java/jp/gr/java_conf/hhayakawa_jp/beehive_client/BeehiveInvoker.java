package jp.gr.java_conf.hhayakawa_jp.beehive_client;

import java.net.HttpCookie;
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
import org.springframework.http.HttpStatus.Series;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jp.gr.java_conf.hhayakawa_jp.beehive_client.exception.Beehive4jException;
import jp.gr.java_conf.hhayakawa_jp.beehive_client.exception.BeehiveHttpClientErrorException;
import jp.gr.java_conf.hhayakawa_jp.beehive_client.exception.BeehiveHttpServerErrorException;
import jp.gr.java_conf.hhayakawa_jp.beehive_client.exception.BeehiveUnexpectedFailureException;
import jp.gr.java_conf.hhayakawa_jp.beehive_client.exception.IllegalInvokerStateException;

abstract class BeehiveInvoker<T> {

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

    ResponseEntity<BeehiveResponse> invoke() throws Beehive4jException {
        if (!isPrepared()) {
//            throw new IllegalInvokerStateException(
//                    ErrorDescription.INVOKER_NOT_CORRECTLY_PREPARED);
            throw new IllegalInvokerStateException();
        }
        HttpEntity<T> entity = new HttpEntity<T>(requestPayload, headers);
        ResponseEntity<BeehiveResponse> result = null;
        try {
            result = template.exchange(makeUrlString() + makeQueryString(),
                    getHttpMethod(), entity, BeehiveResponse.class);
        } catch (RestClientException e) {
            throw new BeehiveUnexpectedFailureException("unexpected filure.", e);
        }
        Series series = result.getStatusCode().series();
        if (HttpStatus.Series.CLIENT_ERROR.equals(series)) {
            throw new BeehiveHttpClientErrorException();
        } else if (HttpStatus.Series.CLIENT_ERROR.equals(series)) {
            throw new BeehiveHttpServerErrorException();
        } 
        return result;
    }

    protected void addHeader(Map<String, String> headers) {
        this.headers.setAll(headers);
    }

    protected void addQuery(Map<String, String> queries) {
        this.urlQueries.putAll(queries);
    }

    protected void setPathValue(String value) {
        this.pathValue = value;
    }

    protected void setRequestPayload(T requestPayload) {
        this.requestPayload = requestPayload;
    }

    protected T getPreparedRequestPayload() {
        return this.requestPayload;
    }

    abstract boolean isPrepared();

    abstract String getApiPath();

    abstract HttpMethod getHttpMethod();

    private void setDefaultHeaders(BeehiveCredential credential) {
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, String> additionalHeaders = new HashMap<String, String>(1);
        HttpCookie sessionCookie = credential.getSession();
        additionalHeaders.put(HttpHeaders.COOKIE,
                sessionCookie.getName() + "=" + sessionCookie.getValue());
        addHeader(additionalHeaders);
    }

    private void setDefaultUrlQueries(BeehiveCredential credential) {
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

}
