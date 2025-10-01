package com.webgram.dgpsn.exceptions;

import org.springframework.security.authentication.InternalAuthenticationServiceException;

/**
 * This exception is thrown in case of a not activated user trying to authenticate.
 */
public class UserNotActivatedException extends InternalAuthenticationServiceException {

    private static final long serialVersionUID = 1L;

    public UserNotActivatedException(String message) {
        super(message);
    }

    public UserNotActivatedException(String message, Throwable t) {
        super(message, t);
    }
}
