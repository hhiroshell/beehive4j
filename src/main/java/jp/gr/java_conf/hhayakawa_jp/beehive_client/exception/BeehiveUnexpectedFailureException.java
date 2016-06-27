package jp.gr.java_conf.hhayakawa_jp.beehive_client.exception;

public class BeehiveUnexpectedFailureException extends Beehive4jRuntimeException {

    public BeehiveUnexpectedFailureException(String message) {
        super(message);
    }

    public BeehiveUnexpectedFailureException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 8665845089078963546L;

}
