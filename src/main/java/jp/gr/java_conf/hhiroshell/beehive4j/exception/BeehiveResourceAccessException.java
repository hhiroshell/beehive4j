package jp.gr.java_conf.hhiroshell.beehive4j.exception;

public class BeehiveResourceAccessException extends BeehiveApiFaultException {

    public BeehiveResourceAccessException(String message) {
        super(message);
    }

    public BeehiveResourceAccessException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 5038829810629401587L;

}
