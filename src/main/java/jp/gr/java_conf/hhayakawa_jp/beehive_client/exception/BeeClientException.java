package jp.gr.java_conf.hhayakawa_jp.beehive_client.exception;

public class BeeClientException extends Exception {

    public BeeClientException(String message) {
        super(message);
    }

    public BeeClientException(Throwable cause) {
        super(cause);
    }

    public BeeClientException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 1463534820891731656L;

}
