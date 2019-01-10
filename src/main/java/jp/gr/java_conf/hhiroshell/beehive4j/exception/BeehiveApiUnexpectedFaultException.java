package jp.gr.java_conf.hhiroshell.beehive4j.exception;

public class BeehiveApiUnexpectedFaultException extends Beehive4jRuntimeException {

    public BeehiveApiUnexpectedFaultException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 8665845089078963546L;

}
