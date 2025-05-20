package com.aitrip.exception.external.amadeus;

/**
 * Exception thrown when a flight search via Amadeus API fails.
 */
public class FlightSearchFailedException extends RuntimeException {

    /**
     * Constructs a new FlightSearchFailedException with the specified detail message.
     *
     * @param message the detail message
     */
    public FlightSearchFailedException(String message) {
        super(message);
    }

    /**
     * Constructs a new FlightSearchFailedException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause the cause
     */
    public FlightSearchFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
