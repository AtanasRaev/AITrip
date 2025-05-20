package com.aitrip.exception.external.amadeus;

/**
 * Exception thrown when hotel offer search fails.
 */
public class HotelOfferSearchFailedException extends RuntimeException {
    public HotelOfferSearchFailedException(String message) {
        super(message);
    }
    public HotelOfferSearchFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
