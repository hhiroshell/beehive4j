package jp.gr.java_conf.hhiroshell.beehive4j;

import org.springframework.http.HttpMethod;

import jp.gr.java_conf.hhiroshell.beehive4j.model.MeetingCreator;

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

}
