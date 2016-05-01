package jp.gr.java_conf.hhayakawa_jp.beehive_client.exception;

public class BeeClientHttpErrorException extends BeeClientException {

    public BeeClientHttpErrorException(ErrorDescription description) {
        super(description);
    }

    public BeeClientHttpErrorException(
            ErrorDescription description, Throwable cause) {
        super(description, cause);
    }

    /**
     * Serial Version UID
     */

    static final long serialVersionUID = -7918364035277747431L;

}
