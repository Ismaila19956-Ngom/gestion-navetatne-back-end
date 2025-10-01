package com.webgram.dgpsn.exceptions;

/**
 * This exception is thrown in case of a not activated user trying to authenticate.
 */
public class UserNotAllowedToValidWorkflowException extends RuntimeException {

    public UserNotAllowedToValidWorkflowException(String message) {
        super(message);
    }

    public UserNotAllowedToValidWorkflowException(String message, Throwable t) {
        super(message, t);
    }
}
