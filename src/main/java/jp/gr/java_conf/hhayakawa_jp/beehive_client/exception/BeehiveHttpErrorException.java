package jp.gr.java_conf.hhayakawa_jp.beehive_client.exception;

import org.springframework.http.HttpStatus;

public class BeehiveHttpErrorException extends BeehiveApiFaultException {

    public BeehiveHttpErrorException(
            String action, String reason, String effect, HttpStatus httpStatus) {
        super(action, reason, effect, httpStatus);
    }

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = -1749533668772995630L;

}
