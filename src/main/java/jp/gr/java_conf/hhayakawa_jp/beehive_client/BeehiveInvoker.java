package jp.gr.java_conf.hhayakawa_jp.beehive_client;

import java.io.IOException;
import java.net.HttpCookie;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jp.gr.java_conf.hhayakawa_jp.beehive_client.exception.BeeClientException;
import jp.gr.java_conf.hhayakawa_jp.beehive_client.exception.BeeClientIllegalInvokerStateException;
import jp.gr.java_conf.hhayakawa_jp.beehive_client.exception.ErrorDescription;

abstract class BeehiveInvoker<T> {

    private final String api_root;

    private HttpHeaders headers = new HttpHeaders();

    private String pathValue = "";

    private Map<String, String> urlQueries = new HashMap<String, String>();

    private T requestPayload;

    public BeehiveInvoker(String api_root, BeehiveCredential credential) {
        this.api_root = api_root;
        setDefaultHeaders(credential);
        setDefaultUrlQueries(credential);
    }

    JsonNode invoke()
            throws JsonProcessingException, IOException, BeeClientException {
        if (!isPrepared()) {
            throw new BeeClientIllegalInvokerStateException(
                    ErrorDescription.INVOKER_NOT_CORRECTLY_PREPARED);
        }
        // header, body
        HttpEntity<T> entity = new HttpEntity<T>(requestPayload, headers);

        RestTemplate restTemplate = new RestTemplate();
        List<ClientHttpRequestInterceptor> interceptors = 
                new ArrayList<ClientHttpRequestInterceptor>(1);
        interceptors.add(new BeehiveRequestLoggingInterceptor());
        restTemplate.setInterceptors(interceptors);
        ResponseEntity<String> result = restTemplate.exchange(
                makeUrlString() + makeQueryString(), getHttpMethod(), entity,
                String.class);
        ObjectMapper mapper = new ObjectMapper();
        String body = result.getBody();
        if (body == null || body.length() == 0) {
            return null;
        }
        return mapper.readTree(body);
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
        bf.append(api_root);
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
