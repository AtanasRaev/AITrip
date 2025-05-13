package com.aitrip.exception.plan;

/**
 * Exception thrown when a PlanCreateDTO is null.
 */
public class NullPlanCreateDTOException extends RuntimeException {
    
    public NullPlanCreateDTOException() {
        super("PlanCreateDTO cannot be null");
    }
    
    public NullPlanCreateDTOException(String message) {
        super(message);
    }
    
    public NullPlanCreateDTOException(String message, Throwable cause) {
        super(message, cause);
    }
}