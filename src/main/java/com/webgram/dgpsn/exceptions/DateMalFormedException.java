package com.webgram.dgpsn.exceptions;

public class DateMalFormedException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public DateMalFormedException(String entity, Object value) {
        super(entity + " not found with the value : " + value);
    }

    public DateMalFormedException(String entity, String realm, String reference) {
        super(entity + " not found with the realm : " + realm + " and reference : " + reference);
    }
    public DateMalFormedException(String realm, String reference) {
        super(realm + reference);
    }

    public DateMalFormedException(String message) {
        super(message);
    }

}
