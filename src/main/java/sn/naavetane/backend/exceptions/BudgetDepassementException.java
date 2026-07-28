package sn.naavetane.backend.exceptions;

/**
 * Exception levée lorsqu'une opération dépasse le montant autorisé du budget
 */
public class BudgetDepassementException extends RuntimeException {
    
    public BudgetDepassementException(String message) {
        super(message);
    }
    
    public BudgetDepassementException(String message, Throwable cause) {
        super(message, cause);
    }
}
