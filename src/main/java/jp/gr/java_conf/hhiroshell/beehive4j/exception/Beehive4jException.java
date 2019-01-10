package jp.gr.java_conf.hhiroshell.beehive4j.exception;

public class Beehive4jException extends Exception {

    public Beehive4jException(String message) {
        super(message);
    }

    public Beehive4jException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = 8819251447943846127L;

}
