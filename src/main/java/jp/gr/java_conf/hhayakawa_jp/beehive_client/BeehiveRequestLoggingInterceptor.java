package jp.gr.java_conf.hhayakawa_jp.beehive_client;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

public class BeehiveRequestLoggingInterceptor
    implements ClientHttpRequestInterceptor {

    private static final Logger logger = 
            Logger.getLogger(BeehiveRequestLoggingInterceptor.class.getName());

    @Override
    public ClientHttpResponse intercept(
            HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {
        if (logger.isDebugEnabled()) {
            logger.debug("REQUEST BODY: " + new String(body));
        }
        return execution.execute(request, body);
    }

}
