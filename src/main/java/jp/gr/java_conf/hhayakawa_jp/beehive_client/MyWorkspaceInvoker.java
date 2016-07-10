package jp.gr.java_conf.hhayakawa_jp.beehive_client;

import org.springframework.http.HttpMethod;

@SuppressWarnings("rawtypes")
public final class MyWorkspaceInvoker extends BeehiveInvoker {

    private static final String PATH = "my/workspace";

    private static final HttpMethod METHOD = HttpMethod.GET;

    public MyWorkspaceInvoker(String api_root, BeehiveCredential credential) {
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
