package jp.gr.java_conf.hhayakawa_jp.beehive_client.exception;

public enum ErrorDescription {
    AUTHENTICATION_FAILED(1, "Failed to authenticate."),
    INVOKER_NOT_CORRECTLY_PREPARED(2, "Invoker is not correctly prepared."),
    UNEXPECTED_HTTP_ERROR(3, "Unexpected http error.")
    ;

    private static final String CODE_HEADER = "BEECLIENT-ERROR-";

    private final String code;
    private final String message;

    ErrorDescription(int code, String message) {
        this.code = CODE_HEADER + String.format("%04d", code);
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getFullDescription() {
        return code + ": " + message;
    }

}
