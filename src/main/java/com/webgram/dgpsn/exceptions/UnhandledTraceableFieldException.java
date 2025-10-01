package com.webgram.dgpsn.exceptions;

public class UnhandledTraceableFieldException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public UnhandledTraceableFieldException(String message) {
        super(message);
    }
}
