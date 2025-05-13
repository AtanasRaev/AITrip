package com.aitrip.exception.external.amadeus;

/**
 * Exception thrown when the Amadeus token response is null or not found.
 */
public class AmadeusTokenNotFoundException extends RuntimeException {
    
    /**
     * Constructs a new AmadeusTokenNotFoundException with the specified detail message.
     * 
     * @param message the detail message
     */
    public AmadeusTokenNotFoundException(String message) {
        super(message);
    }
    
    /**
     * Constructs a new AmadeusTokenNotFoundException with the specified detail message and cause.
     * 
     * @param message the detail message
     * @param cause the cause
     */
    public AmadeusTokenNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}