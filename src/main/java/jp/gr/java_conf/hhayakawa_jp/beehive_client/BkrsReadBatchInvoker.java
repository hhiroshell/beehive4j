package jp.gr.java_conf.hhayakawa_jp.beehive_client;

import org.springframework.http.HttpMethod;

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
        if (!(payload instanceof BeeIdListPayload)) {
            return false;
        }
        return true;
    }

}
