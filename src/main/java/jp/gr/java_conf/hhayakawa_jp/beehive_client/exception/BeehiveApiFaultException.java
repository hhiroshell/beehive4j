package jp.gr.java_conf.hhayakawa_jp.beehive_client.exception;

import org.springframework.http.HttpStatus;

public class BeehiveApiFaultException extends Beehive4jException {

    private final String action;
    private final String reason;
    private final String effect;
    private final HttpStatus httpStatus;

    public BeehiveApiFaultException(
            String action, String reason, String effect, HttpStatus httpStatus) {
        super(httpStatus.toString() + ": " + reason);
        this.action = action;
        this.reason = reason;
        this.effect = effect;
        this.httpStatus = httpStatus;
    }

    public String getAction() {
        return action;
    }

    public String getReason() {
        return reason;
    }

    public String getEffect() {
        return effect;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 1463534820891731656L;

}
