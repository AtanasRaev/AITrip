package com.aitrip.exception.prompt;

/**
 * Exception thrown when a PromptDTO is null.
 */
public class NullPromptException extends PromptValidationException {
    
    public NullPromptException() {
        super("PromptDTO cannot be null");
    }
    
    public NullPromptException(String message) {
        super(message);
    }
    
    public NullPromptException(String message, Throwable cause) {
        super(message, cause);
    }
}