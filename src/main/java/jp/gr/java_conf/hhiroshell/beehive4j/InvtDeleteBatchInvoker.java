package jp.gr.java_conf.hhiroshell.beehive4j;

import org.springframework.http.HttpMethod;

import jp.gr.java_conf.hhiroshell.beehive4j.model.BeeIdList;

public final class InvtDeleteBatchInvoker extends BeehiveInvoker<BeeIdList> {

    private static final String PATH = "invt/delete";

    private static final HttpMethod METHOD = HttpMethod.DELETE;

    public InvtDeleteBatchInvoker(String api_root, BeehiveCredential credential) {
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
