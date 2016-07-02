package jp.gr.java_conf.hhayakawa_jp.beehive_client;

import org.springframework.http.HttpMethod;

import jp.gr.java_conf.hhayakawa_jp.beehive_client.model.MeetingCreator;

public final class InvtCreateInvoker extends BeehiveInvoker<MeetingCreator> {

    private static final String PATH = "invt";

    private static final HttpMethod METHOD = HttpMethod.POST;

    public InvtCreateInvoker(String api_root, BeehiveCredential credential) {
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

    @Override
    boolean isPrepared() {
        // TODO implement
        return true;
    }

}
