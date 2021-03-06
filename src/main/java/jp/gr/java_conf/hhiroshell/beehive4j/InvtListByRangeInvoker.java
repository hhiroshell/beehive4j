package jp.gr.java_conf.hhiroshell.beehive4j;

import org.springframework.http.HttpMethod;

import jp.gr.java_conf.hhiroshell.beehive4j.model.CalendarRange;

public final class InvtListByRangeInvoker extends BeehiveInvoker<CalendarRange> {

    private static final String PATH = "invt/list/byRange";

    private static final HttpMethod METHOD = HttpMethod.POST;

    public InvtListByRangeInvoker(String api_root, BeehiveCredential credential) {
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