package com.webgram.dgpsn.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.webgram.dgpsn.exceptions.*;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {

    @Autowired
    private ObjectMapper objectMapper;

    private static final Logger logger = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Error invalidInput(MethodArgumentNotValidException e) {

        logger.error(e.getMessage());

        Error error = new Error();
        error.setCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
        error.setMessage("unable to process the contained instructions");
        error.setErrors(e.getBindingResult().getFieldErrors().stream().map(objectError -> objectError.getField() + " " + objectError.getDefaultMessage()).collect(Collectors.toList()));

        return error;
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public Error notFound(ResourceNotFoundException e) {

        logger.error(e.getMessage());

        Error error = new Error();
        error.setCode(HttpStatus.NOT_FOUND.value());
        error.setMessage(e.getMessage());

        return error;
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidHttpException.class)
    public Error argumentNonValid(MethodArgumentNotValidHttpException e) {

        logger.error(e.getMessage());

        Error error = new Error();
        error.setCode(HttpStatus.BAD_REQUEST.value());
        error.setMessage(e.getMessage());

        return error;
    }

    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ResourceUnauthorizedException.class)
    public Error unauthorized(final ResourceUnauthorizedException e) {

        logger.error("Unauthorized access to remote server.", e);

        final Error error = new Error();
        error.setCode(HttpStatus.UNAUTHORIZED.value());
        error.setMessage(e.getMessage());

        if (StringUtils.isNotEmpty(e.getResponseBodyAsString())) {
            final Error errorRemote = decode(e.getResponseBodyAsString());
            if (errorRemote != null) {
                error.setMessage(e.getMessage() + " => " + errorRemote.getMessage());
                error.setErrors(errorRemote.getErrors());
            }
        }

        return error;
    }

    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ExceptionHandler(ForbiddenActionException.class)
    public Error forbidden(final ForbiddenActionException e) {

        logger.error("Access to remote server is forbidden.", e);

        final Error error = new Error();
        error.setCode(HttpStatus.FORBIDDEN.value());
        error.setMessage(e.getMessage());

        if (StringUtils.isNotEmpty(e.getResponseBodyAsString())) {
            final Error errorRemote = decode(e.getResponseBodyAsString());
            if (errorRemote != null) {
                error.setMessage(e.getMessage() + " => " + errorRemote.getMessage());
                error.setErrors(errorRemote.getErrors());
            }
        }

        return error;
    }

    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ExceptionHandler(ResourceAlreadyExistException.class)
    public Error conflict(ResourceAlreadyExistException e) {

        logger.error(e.getMessage());

        Error error = new Error();
        error.setCode(HttpStatus.CONFLICT.value());
        error.setMessage(e.getMessage());

        return error;
    }

    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ExceptionHandler(ResourceAlreadyExistHttpException.class)
    public Error conflict(ResourceAlreadyExistHttpException e) {

        logger.error(e.getMessage());
        Error error = new Error();
        error.setCode(HttpStatus.CONFLICT.value());
        error.setMessage(e.getMessage());
        if (e.getResponseBodyAsString() != null) {
            error.setErrors(new LinkedList<>());
            error.getErrors().add(e.getResponseBodyAsString());
        }
        return error;
    }

    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Error invalidInput(final HttpMessageNotReadableException e) {

        logger.error("HttpMessageNotReadableException: {}", e.getMessage());

        Throwable rootCause = e;
        while(rootCause.getCause() != null) {
            rootCause = rootCause.getCause();
        }

        final Error error = new Error();
        error.setCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
        error.setMessage(rootCause.getMessage());

        return error;
    }

    @ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(PublishProjectOutOfBoundsException.class)
    public Error publishProjectError(PublishProjectOutOfBoundsException e) {

        logger.error("publishProjectError: {}", e.getMessage());

        Error error = new Error();
        error.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setMessage(e.getMessage());

        return error;
    }

    @ResponseStatus(value = HttpStatus.LOCKED)
    @ExceptionHandler(UserNotActivatedException.class)
    public Error userNotActivated(UserNotActivatedException e) {

        logger.error("userNotActivatedError: {}", e.getMessage());

        Error error = new Error();
        error.setCode(HttpStatus.LOCKED.value());
        error.setMessage(e.getMessage());

        return error;
    }

    @ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(BadCredentialsException.class)
    public Error badCredentials(BadCredentialsException e) {

        logger.error("badCredentials: {}", e.getMessage());

        Error error = new Error();
        error.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setMessage(e.getMessage());

        return error;
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Error internalError(Exception e) {

        logger.error("internalError", e);

        Error error = new Error();
        error.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setMessage("An unexpected server error occurred.");

        return error;
    }

    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ExceptionHandler(NotValidPeriodException.class)
    public Error notValidPeriod(NotValidPeriodException e) {

        logger.error(e.getMessage());

        Error error = new Error();
        error.setCode(HttpStatus.CONFLICT.value());
        error.setMessage(e.getMessage());

        return error;
    }

    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(PasswordException.class)
    public Error passwordException(PasswordException e) {

        logger.error(e.getMessage());

        Error error = new Error();
        error.setCode(HttpStatus.UNAUTHORIZED.value());
        error.setMessage(e.getMessage());

        return error;
    }

    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UserDisabledException.class)
    public Error passwordDisabledException(UserDisabledException e) {

        logger.error(e.getMessage());

        Error error = new Error();
        error.setCode(HttpStatus.UNAUTHORIZED.value());
        error.setMessage(e.getMessage());

        return error;
    }

    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ExpiredJwtException.class)
    public Error expiredJwtException(ExpiredJwtException e) {

        logger.error(e.getMessage());

        Error error = new Error();
        error.setCode(HttpStatus.UNAUTHORIZED.value());
        error.setMessage(e.getMessage());

        return error;
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ConstraintViolationException.class)
    public Error constraintViolation(ConstraintViolationException e) {

        logger.error(e.getMessage());

        Error error = new Error();
        error.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setMessage(e.getMessage());

        return error;
    }
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public Error dateConstraintViolation(DataIntegrityViolationException e) {

        logger.error(e.getMessage());

        Error error = new Error();
        error.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setMessage(e.getMessage());

        return error;
    }

    //InvalidTemporaryCodeException

//    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(InvalidTemporaryCodeException.class)
//    public Error invalidTemporaryCodeException(InvalidTemporaryCodeException e) {
//        logger.error(e.getMessage());
//        Error error = new Error();
//        error.setCode(HttpStatus.BAD_REQUEST.value());
//        error.setMessage(e.getMessage());
//        return error;
//    }


    private Error decode(final String data) {

        try {
            return objectMapper.readValue(data, Error.class);
        } catch (final IOException e) {
            logger.warn("Cannot decode error received from distant server.", e);
            return null;
        }
    }

    static class Error {

        private Integer code;
        private String message;
        private List<String> errors;

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public List<String> getErrors() {
            return errors;
        }

        public void setErrors(List<String> errors) {
            this.errors = errors;
        }
    }
}
