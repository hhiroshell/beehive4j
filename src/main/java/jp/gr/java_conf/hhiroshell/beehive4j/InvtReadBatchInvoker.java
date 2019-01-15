package jp.gr.java_conf.hhiroshell.beehive4j;

import org.springframework.http.HttpMethod;

import jp.gr.java_conf.hhiroshell.beehive4j.model.BeeIdList;

public final class InvtReadBatchInvoker extends BeehiveInvoker<BeeIdList> {

    private static final String PATH = "invt/read";

    private static final HttpMethod METHOD = HttpMethod.POST;

    public InvtReadBatchInvoker(String api_root, BeehiveCredential credential) {
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