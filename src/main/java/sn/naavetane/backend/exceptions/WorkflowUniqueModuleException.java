package sn.naavetane.backend.exceptions;

/**
 * This exception is thrown in case of a not activated user trying to authenticate.
 */
public class WorkflowUniqueModuleException extends RuntimeException {

    public WorkflowUniqueModuleException(String message) {
        super(message);
    }

    public WorkflowUniqueModuleException(String message, Throwable t) {
        super(message, t);
    }
}
