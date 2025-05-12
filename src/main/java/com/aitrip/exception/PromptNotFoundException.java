package com.aitrip.exception;

/**
 * Exception thrown when a Prompt with a specific ID is not found.
 */
public class PromptNotFoundException extends PromptValidationException {
    
    public PromptNotFoundException() {
        super("Prompt not found with the specified ID");
    }
    
    public PromptNotFoundException(String message) {
        super(message);
    }
    
    public PromptNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}