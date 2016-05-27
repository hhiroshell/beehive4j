package jp.gr.java_conf.hhayakawa_jp.beehive_client;

import org.springframework.http.HttpMethod;

final class InvtListByRangeInvoker extends BeehiveInvoker {

    private static final String PATH = "invt/list/byRange";

    private static final HttpMethod METHOD = HttpMethod.POST;

    InvtListByRangeInvoker(String api_root, BeehiveCredential credential) {
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
        if (!(payload instanceof CalendarRangePayload)) {
            return false;
        }
        return true;
    }

}
