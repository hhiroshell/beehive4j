package jp.gr.java_conf.hhayakawa_jp.beehive_client;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.HttpCookie;
import java.net.URL;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import jp.gr.java_conf.hhayakawa_jp.beehive_client.exception.BeeClientException;
import jp.gr.java_conf.hhayakawa_jp.beehive_client.exception.BeeClientUnauthorizedException;
import jp.gr.java_conf.hhayakawa_jp.beehive_client.exception.ErrorDescription;
import jp.gr.java_conf.hhayakawa_jp.beehive_client.exception.BeeClientHttpErrorException;

// TODO: Beehiveとのセッションタイムアウトを考慮する
public class BeehiveContext {

    private static final String BEEHIVE_API_CONTEXT_ROOT = "comb/v1/d/";

    private final BeehiveCredential credential;

    private final String api_root;

    private BeehiveContext(String api_root, BeehiveCredential credential) {
        this.api_root = api_root;
        this.credential = credential;
    }

    public static BeehiveContext getBeehiveContext(
            URL host, String user, String password) throws BeeClientException {
        if (user == null || user.length() == 0 
                || password == null || password.length() == 0) {
            throw new NullPointerException(
                    "User name or password is not specified.");
        }
        String basicAuthHeader = makeBasicAuthString(user, password);
        return getBeehiveContext(host, basicAuthHeader);
    }

    public static BeehiveContext getBeehiveContext(
            URL host, String basicAuthHeader) throws BeeClientException {
        if (host == null) {
            throw new NullPointerException(
                    "Destination URL is not specified.");
        }
        if (basicAuthHeader == null) {
            throw new NullPointerException(
                    "Basic auth header is not specified.");
        }
        String api_root = makeApiRootString(host);
        return new BeehiveContext(api_root, login(api_root, basicAuthHeader));
    }

    private static BeehiveCredential login(
            String api_root, String basicAuthHeader)
            throws BeeClientHttpErrorException, BeeClientUnauthorizedException {
        // header
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.set(HttpHeaders.AUTHORIZATION, basicAuthHeader);
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        // invoke
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<AntiCsrfToken> result = null;
        try {
            result = restTemplate.exchange(api_root + "session/login",
                    HttpMethod.POST, entity, AntiCsrfToken.class);
        } catch (RestClientException e) {
            if (e instanceof HttpClientErrorException) {
                HttpClientErrorException ce = (HttpClientErrorException)e;
                HttpStatus status = ce.getStatusCode();
                if (HttpStatus.UNAUTHORIZED.equals(status)) {
                     new BeeClientUnauthorizedException(
                            ErrorDescription.AUTHENTICATE_FAILED, ce);
                }
            }
            throw new BeeClientHttpErrorException(
                    ErrorDescription.UNEXPECTED_HTTP_ERROR, e);
        }

        // parse session cookie
        List<String> sessionHeader = result.getHeaders().get("Set-Cookie");
        if (sessionHeader == null || sessionHeader.size() == 0) {
            throw new IllegalStateException("Cookie is not set.");
        }
        HttpCookie jsessionid = parseCookie(sessionHeader, "JSESSIONID");
        if (jsessionid == null) {
            throw new IllegalStateException("JSESSIONID is not set.");
        }
        return new BeehiveCredential(jsessionid, result.getBody().getToken());
    }

    private static String makeApiRootString(URL host) {
        StringBuffer buf = new StringBuffer();
        buf.append(host.getProtocol());
        buf.append("://");
        buf.append(host.getHost());
        int port = host.getPort();
        if (port >= 0) {
            buf.append(":");
            buf.append(port);
        }
        buf.append("/");
        buf.append(BEEHIVE_API_CONTEXT_ROOT);
        return buf.toString();
    }

    private static String makeBasicAuthString(String user, String password) {
        String src = user.trim() + ":" + password;
        byte[] encoded = Base64.getEncoder().encode(src.getBytes());
        return new String(encoded);
    }

    private static HttpCookie parseCookie(
            List<String> setCookieHeader, String cookieName) {
        String[] attributes = null;
        for (String elm : setCookieHeader) {
            if (elm.startsWith(cookieName + "=")) {
                attributes = elm.split(";");
                break;
            }
        }
        if (attributes == null || attributes.length == 0) {
            return null;
        }
        String[] keyValue = attributes[0].split("=");
        if (keyValue.length != 2) {
            throw new IllegalStateException("Invalid cookie is going to bee set.");
        }
        HttpCookie cookie = new HttpCookie(
                keyValue[0].trim(), keyValue[1].trim());
        return cookie;
    }

    boolean isActive() {
        // TODO: 実装
        return false;
    }

    public <T extends BeehiveInvoker<?>> T getInvoker(Class<T> InvokerType) {
        if (this.credential == null) {
            // TODO: 認証されていない旨のエラー
        }
        try {
            Class<?>[] argTypes = {String.class, BeehiveCredential.class};
            Constructor<T> constructor = InvokerType.getConstructor(argTypes);
            Object[] args = {this.api_root, this.credential};
            T invoker = constructor.newInstance(args);
            return invoker;
        } catch (NoSuchMethodException | SecurityException |
                InstantiationException | IllegalAccessException |
                IllegalArgumentException | InvocationTargetException e) {
            throw new IllegalStateException("Failed to create a invoker.", e);
        }
    }

}
