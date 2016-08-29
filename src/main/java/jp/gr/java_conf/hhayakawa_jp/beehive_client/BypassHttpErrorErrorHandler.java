package jp.gr.java_conf.hhayakawa_jp.beehive_client;

import java.io.IOException;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

public class BypassHttpErrorErrorHandler implements ResponseErrorHandler {

    @Override
    public void handleError(ClientHttpResponse arg0) throws IOException {
    }

    @Override
    public boolean hasError(ClientHttpResponse arg0) throws IOException {
        return false;
    }

}
