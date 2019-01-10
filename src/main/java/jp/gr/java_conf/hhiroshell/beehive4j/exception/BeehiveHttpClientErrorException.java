package jp.gr.java_conf.hhiroshell.beehive4j.exception;

import org.springframework.http.HttpStatus;

public class BeehiveHttpClientErrorException extends BeehiveHttpErrorException {

    public BeehiveHttpClientErrorException(
            String action, String reason, String effect, HttpStatus httpStatus) {
        super(action, reason, effect, httpStatus);
    }

    /**
     * Serial Version UID
     */
    static final long serialVersionUID = -7918364035277747431L;

}
