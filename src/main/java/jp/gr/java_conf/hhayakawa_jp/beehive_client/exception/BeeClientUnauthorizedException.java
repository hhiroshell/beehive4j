package jp.gr.java_conf.hhayakawa_jp.beehive_client.exception;

public class BeeClientUnauthorizedException extends BeeClientException {

    public BeeClientUnauthorizedException(String message) {
        super(message);
    }

    public BeeClientUnauthorizedException(Throwable cause) {
        super(cause);
    }

    public BeeClientUnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 5975649818037514598L;

}
