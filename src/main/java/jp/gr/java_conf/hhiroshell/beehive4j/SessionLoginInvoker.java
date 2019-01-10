package jp.gr.java_conf.hhiroshell.beehive4j;

import org.springframework.http.HttpMethod;

public final class SessionLoginInvoker extends BeehiveInvoker<Object> {

    private static final String PATH = "session/login";

    private static final HttpMethod METHOD = HttpMethod.POST;

    public SessionLoginInvoker(String api_root, BeehiveCredential credential) {
        super(api_root, credential);
    }

    @Override
    String getApiPath() {
        return PATH;
    }

    @Override
    HttpMethod getHttpMethod() {
        return METHOD;
    }

}
