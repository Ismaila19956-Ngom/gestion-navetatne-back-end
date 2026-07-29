package sn.naavetane.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class QuotaException extends RuntimeException {
    public QuotaException(String message) {
        super(message);
    }
}
