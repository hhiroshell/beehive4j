package jp.gr.java_conf.hhayakawa_jp.beehive4j;

import org.springframework.http.HttpMethod;

import jp.gr.java_conf.hhayakawa_jp.beehive4j.model.MeetingUpdater;

public final class InvtUpdateInvoker extends BeehiveInvoker<MeetingUpdater> {

    private static final String PATH = "invt";

    private static final HttpMethod METHOD = HttpMethod.PUT;

    public InvtUpdateInvoker(String api_root, BeehiveCredential credential) {
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
