package com.webgram.dgpsn.exceptions;

/**
 * This exception is thrown in case of a not activated user trying to authenticate.
 */
public class UserDisabledException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UserDisabledException(String message) {
        super(message);
    }

    public UserDisabledException(String message, Throwable t) {
        super(message, t);
    }
}
