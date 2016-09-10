package jp.gr.java_conf.hhayakawa_jp.beehive4j.exception;

public enum ErrorDescription {
    AUTHENTICATION_FAILED(1, "Failed to authenticate."),
    SESSION_EXPIRED(2, "Session is expired for some reason, such as time-out."),
    INVOKER_NOT_CORRECTLY_PREPARED(3, "Invoker is not correctly prepared."),
    UNEXPECTED_HTTP_ERROR(4, "Unexpected http error.")
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
