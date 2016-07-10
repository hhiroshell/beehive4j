package jp.gr.java_conf.hhayakawa_jp.beehive_client;

import org.springframework.http.HttpMethod;

import jp.gr.java_conf.hhayakawa_jp.beehive_client.model.BeeIdList;

public final class BkrsReadBatchInvoker extends BeehiveInvoker<BeeIdList> {

    private static final String PATH = "bkrs/read";

    private static final HttpMethod METHOD = HttpMethod.POST;

    public BkrsReadBatchInvoker(String api_root, BeehiveCredential credential) {
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
