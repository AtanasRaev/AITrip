package com.aitrip.exception.prompt;

/**
 * Exception thrown when a user prompt is null or empty.
 */
public class EmptyUserPromptException extends PromptValidationException {
    
    public EmptyUserPromptException() {
        super("User prompt cannot be null or empty");
    }
    
    public EmptyUserPromptException(String message) {
        super(message);
    }
    
    public EmptyUserPromptException(String message, Throwable cause) {
        super(message, cause);
    }
}