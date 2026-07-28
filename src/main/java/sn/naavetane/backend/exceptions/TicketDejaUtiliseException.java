package sn.naavetane.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class TicketDejaUtiliseException extends RuntimeException {
    public TicketDejaUtiliseException(String message) {
        super(message);
    }
}
