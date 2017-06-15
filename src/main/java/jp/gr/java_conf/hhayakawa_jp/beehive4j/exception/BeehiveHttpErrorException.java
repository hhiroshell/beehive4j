package jp.gr.java_conf.hhayakawa_jp.beehive4j.exception;

import org.springframework.http.HttpStatus;

/**
 * beehiveのロジックで生成されたエラーレスポンスを受け取ったときにthrowされる例外
 */
public class BeehiveHttpErrorException extends BeehiveApiFaultException {

    private final String action;
    private final String reason;
    private final String effect;
    private final HttpStatus httpStatus;

    public BeehiveHttpErrorException(
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
    private static final long serialVersionUID = -1749533668772995630L;

}
