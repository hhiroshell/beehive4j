package jp.gr.java_conf.hhayakawa_jp.beehive4j.exception;

public class BeehiveUnauthorizedException extends BeehiveResourceAccessException {

    public BeehiveUnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 6878674376547082464L;

}
