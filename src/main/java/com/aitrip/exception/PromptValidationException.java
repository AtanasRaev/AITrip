package com.aitrip.exception;

/**
 * Base exception class for prompt validation errors.
 */
public class PromptValidationException extends RuntimeException {
    
    public PromptValidationException(String message) {
        super(message);
    }
    
    public PromptValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}