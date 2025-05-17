package com.aitrip.exception.external.amadeus;

import lombok.Getter;

/**
 * Exception thrown when no hotel offers are available for the selected dates and destination.
 */
@Getter
public class NoHotelOffersAvailableException extends RuntimeException {

    private final String destination;
    private final String startDate;
    private final String endDate;
    
    /**
     * Constructs a new NoHotelOffersAvailableException with the specified detail message and search parameters.
     * 
     * @param message the detail message
     * @param destination the destination location
     * @param startDate the start date
     * @param endDate the end date
     */
    public NoHotelOffersAvailableException(String message, String destination, 
                                          String startDate, String endDate) {
        super(message);
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}