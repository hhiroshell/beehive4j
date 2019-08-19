package jp.gr.java_conf.hhiroshell.beehive4j;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.*;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

class BeehiveRequestLoggingInterceptor implements ClientHttpRequestInterceptor {

    private static final String ENV_KEY_LOG_LEVEL = "BEEHIVE4J_LOG_LEVEL";

    private static final String ENV_VALUE_LOG_LEVEL_DEFAULT = "WARNING";

    private static final Logger logger = Logger.getLogger(BeehiveRequestLoggingInterceptor.class.getName());

    static {
        Optional<String> logLevel = Optional.ofNullable(System.getenv(ENV_KEY_LOG_LEVEL));
        logger.setLevel(Level.parse(logLevel.orElse(ENV_VALUE_LOG_LEVEL_DEFAULT)));
        Handler handler = new ConsoleHandler();
        handler.setFormatter(new SimpleFormatter());
        logger.addHandler(handler);
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {
        logger.fine("REQUEST BODY: " + new String(body));
        return execution.execute(request, body);
    }

}
