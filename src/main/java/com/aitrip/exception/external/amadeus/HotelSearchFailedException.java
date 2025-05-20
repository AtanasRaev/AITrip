package com.aitrip.exception.external.amadeus;

/**
 * Thrown when hotel search by city fails or returns no results.
 */
public class HotelSearchFailedException extends RuntimeException {
    public HotelSearchFailedException(String message) {
        super(message);
    }

    public HotelSearchFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
