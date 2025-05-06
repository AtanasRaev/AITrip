package com.aitrip.exception;

/**
 * Exception thrown when a system prompt is null or empty.
 */
public class EmptySystemPromptException extends PromptValidationException {
    
    public EmptySystemPromptException() {
        super("System prompt cannot be null or empty");
    }
    
    public EmptySystemPromptException(String message) {
        super(message);
    }
    
    public EmptySystemPromptException(String message, Throwable cause) {
        super(message, cause);
    }
}