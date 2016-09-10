package jp.gr.java_conf.hhayakawa_jp.beehive4j.exception;

import org.springframework.http.HttpStatus;

public class BeehiveHttpServerErrorException extends BeehiveHttpErrorException {

    public BeehiveHttpServerErrorException(
            String action, String reason, String effect, HttpStatus httpStatus) {
        super(action, reason, effect, httpStatus);
    }

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = -696985259497788451L;

}
