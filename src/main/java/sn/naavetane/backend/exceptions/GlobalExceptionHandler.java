package sn.naavetane.backend.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Intercepte toutes les ResponseStatusException (ex: throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Message"))
     * et les renvoie sous forme de JSON lisible par Angular.
     */
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> handleResponseStatusException(ResponseStatusException ex) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("status", ex.getStatusCode().value());
        errorDetails.put("message", ex.getReason()); // Extrait le message personnalisé ("AUGMENTATION_INTERDITE...")
        errorDetails.put("error", ex.getStatusCode().toString());
        
        return ResponseEntity.status(ex.getStatusCode()).body(errorDetails);
    }

    @ExceptionHandler(QuotaException.class)
    public ResponseEntity<Map<String, Object>> handleQuotaException(QuotaException ex) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("status", 403);
        errorDetails.put("message", ex.getMessage()); 
        errorDetails.put("error", "Forbidden");
        
        return ResponseEntity.status(403).body(errorDetails);
    }
}
