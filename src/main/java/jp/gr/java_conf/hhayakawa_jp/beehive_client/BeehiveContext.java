package jp.gr.java_conf.hhayakawa_jp.beehive_client;

import java.net.HttpCookie;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import jp.gr.java_conf.hhayakawa_jp.beehive_client.exception.BClientException;
import jp.gr.java_conf.hhayakawa_jp.beehive_client.exception.BClientUnauthorizedException;

// TODO: Beehiveとのセッションタイムアウトを考慮する
public class BeehiveContext {

    private BeehiveCredential credential = null;

    public static BeehiveContext getBeehiveContext(String user, String password)
            throws BClientException {
        BeehiveContext context = new BeehiveContext();
        context.credential = login(user, password);
        return context;
    }

    /**
     *  TODO: パスワード誤りなどの認証エラーをケアする
     *  
     * @throws BClientUnauthorizedException 
     */
    private static BeehiveCredential login(String user, String password)
            throws BClientUnauthorizedException {
        if (user == null || user.length() == 0 
                || password == null || password.length() == 0) {
            throw new NullPointerException(
                    "user name or password is not specified.");
        }

        // header
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.set(HttpHeaders.AUTHORIZATION,
                "Basic " + makeBasicAuthString(user, password));
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        // invoke
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<AntiCsrfToken> result = restTemplate.exchange(
                Constants.BEEHIVE_API_ROOT + "session/login", HttpMethod.POST,
                entity, AntiCsrfToken.class);

        // parse session cookie
        List<String> sessionHeader = result.getHeaders().get("Set-Cookie");
        if (sessionHeader == null || sessionHeader.size() == 0) {
            throw new BClientUnauthorizedException();
        }
        HttpCookie jsessionid = parseCookie(sessionHeader, "JSESSIONID");
        if (jsessionid == null) {
            throw new BClientUnauthorizedException();
        }
        return new BeehiveCredential(jsessionid, result.getBody().getToken());
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
            throw new IllegalArgumentException("Invalid cookie is going to bee set.");
        }
        HttpCookie cookie = new HttpCookie(
                keyValue[0].trim(), keyValue[1].trim());
        return cookie;
    }

    boolean isActive() {
        // TODO: 実装
        return false;
    }

    public BeehiveInvoker getInvoker(BeehiveApiDefinitions api) {
        if (this.credential == null) {
            // TODO: 認証されていない旨のエラー
        }
        switch (api) {
            case INVT_LIST_BYRANGE:
                return new InvtListByRangeInvoker(this.credential);
            case INVT_READ_BATCH:
                return new InvtReadBatchInvoker(this.credential);
            case BKRS_READ_BATCH:
                return new BkrsReadBatchInvoker(this.credential);
            default:
                return null;
        }
    }

}
