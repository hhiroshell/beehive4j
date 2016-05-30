package jp.gr.java_conf.hhayakawa_jp.beehive_client;

import org.springframework.http.HttpMethod;

import jp.gr.java_conf.hhayakawa_jp.beehive_client.model.BeeIdList;

final class BkrsReadBatchInvoker extends BeehiveInvoker {

    private static final String PATH = "bkrs/read";

    private static final HttpMethod METHOD = HttpMethod.POST;

    BkrsReadBatchInvoker(String api_root, BeehiveCredential credential) {
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
    protected boolean isPrepared() {
        BeehiveApiPayload payload = getBody();
        if (payload == null) {
            return false;
        }
        if (!(payload instanceof BeeIdList)) {
            return false;
        }
        return true;
    }

}
