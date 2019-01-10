package jp.gr.java_conf.hhiroshell.beehive4j.exception;

public class BeehiveApiFaultException extends Beehive4jException {

    public BeehiveApiFaultException(String message) {
        super(message);
    }

    public BeehiveApiFaultException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 1463534820891731656L;

}
