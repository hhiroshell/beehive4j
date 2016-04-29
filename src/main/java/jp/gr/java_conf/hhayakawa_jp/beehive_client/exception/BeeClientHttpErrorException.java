package jp.gr.java_conf.hhayakawa_jp.beehive_client.exception;

public class BeeClientHttpErrorException extends BeeClientException {

    public BeeClientHttpErrorException(String message) {
        super(message);
    }

    public BeeClientHttpErrorException(Throwable cause) {
        super(cause);
    }

    public BeeClientHttpErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Serial Version UID
     */

    static final long serialVersionUID = -7918364035277747431L;

}
