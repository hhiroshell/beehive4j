package jp.gr.java_conf.hhayakawa_jp.beehive_client.exception;

public class BeeClientException extends Exception {

    private final ErrorDescription description;

    public BeeClientException(ErrorDescription description) {
        super(description.getFullDescription());
        this.description = description;
    }

    public BeeClientException(
            ErrorDescription description, Throwable cause) {
        super(description.getFullDescription(), cause);
        this.description = description;
    }

    public ErrorDescription getErrorDescription() {
        return description;
    }

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 1463534820891731656L;

}
