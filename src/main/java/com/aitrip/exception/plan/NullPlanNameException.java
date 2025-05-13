package com.aitrip.exception.plan;

/**
 * Exception thrown when a plan name is null.
 */
public class NullPlanNameException extends RuntimeException {
    
    public NullPlanNameException() {
        super("Plan name cannot be null");
    }
    
    public NullPlanNameException(String message) {
        super(message);
    }
    
    public NullPlanNameException(String message, Throwable cause) {
        super(message, cause);
    }
}