package jp.gr.java_conf.hhayakawa_jp.beehive4j.exception;

public class Beehive4jRuntimeException extends RuntimeException {

    public Beehive4jRuntimeException(String message) {
        super(message);
    }

    public Beehive4jRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 3882464601978800477L;

}
