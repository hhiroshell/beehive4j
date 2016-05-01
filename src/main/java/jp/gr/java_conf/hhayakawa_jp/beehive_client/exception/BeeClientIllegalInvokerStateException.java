package jp.gr.java_conf.hhayakawa_jp.beehive_client.exception;

public class BeeClientIllegalInvokerStateException extends BeeClientException {

    public BeeClientIllegalInvokerStateException(ErrorDescription description) {
        super(description);
    }

    public BeeClientIllegalInvokerStateException(
            ErrorDescription description, Throwable cause) {
        super(description, cause);
    }

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = -3448721928916105305L;

}
