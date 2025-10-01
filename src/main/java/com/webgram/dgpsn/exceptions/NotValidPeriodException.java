package com.webgram.dgpsn.exceptions;

public class NotValidPeriodException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public NotValidPeriodException(String entity, Object value) {
        super(entity + " : " + value);
    }

    public NotValidPeriodException(String entity, String realm, String reference) {
        super(entity + " not found with the realm : " + realm + " and reference : " + reference);
    }

    public NotValidPeriodException(String message) {
        super(message);
    }

}
