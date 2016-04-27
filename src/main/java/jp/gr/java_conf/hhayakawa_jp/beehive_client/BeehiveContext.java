package jp.gr.java_conf.hhayakawa_jp.beehive_client;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

// TODO: Beehiveとのセッションタイムアウトを考慮する
class BeehiveContext {

    private BeehiveCredential credential = null;

    static BeehiveContext getBeehiveContext(String user, String password) {
        BeehiveContext context = new BeehiveContext();
        context.credential = login(user, password);
        return context;
    }

    /**
     *  TODO: パスワード誤りなどの認証エラーをケアする
     */
    private static BeehiveCredential login(String user, String password) {
        if (user == null || user.length() == 0 
                || password == null || password.length() == 0) {
            throw new NullPointerException(
                    "user name or password is not specified.");
        }
        // header
        // TODO: 入力値のチェック
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
        List<String> cookies = result.getHeaders().get("Set-Cookie");
        if (cookies == null || cookies.size() == 0) {
            // TODO: エラー処理
        }
        String session = null;
        for (String cookie : cookies) {
            // TODO: できればCookieオブジェクトにしておきたい
            if (cookie.startsWith("JSESSIONID")) {
                String[] a = cookie.split(";");
                session = a[0];
                break;
            }
        }
        return new BeehiveCredential(session, result.getBody().getToken());
    }

    private static String makeBasicAuthString(String user, String password) {
        String src = user + ":" + password;
        byte[] encoded = Base64.getEncoder().encode(src.getBytes());
        return new String(encoded);
    }

    boolean isActive() {
        // TODO: 実装
        return false;
    }

    BeehiveInvoker getInvoker(BeehiveApiDefinitions api) {
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
