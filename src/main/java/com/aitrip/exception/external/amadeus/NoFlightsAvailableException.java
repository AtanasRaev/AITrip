package com.aitrip.exception.external.amadeus;

import lombok.Getter;

/**
 * Exception thrown when no flights are available for the selected dates and destinations.
 */
@Getter
public class NoFlightsAvailableException extends RuntimeException {

    /**
     * -- GETTER --
     *  Gets the origin location.
     *
     * @return the origin location
     */
    private final String origin;
    /**
     * -- GETTER --
     *  Gets the destination location.
     *
     * @return the destination location
     */
    private final String destination;
    /**
     * -- GETTER --
     *  Gets the start date.
     *
     * @return the start date
     */
    private final String startDate;
    /**
     * -- GETTER --
     *  Gets the end date.
     *
     * @return the end date
     */
    private final String endDate;
    
    /**
     * Constructs a new NoFlightsAvailableException with the specified detail message and search parameters.
     * 
     * @param message the detail message
     * @param origin the origin location
     * @param destination the destination location
     * @param startDate the start date
     * @param endDate the end date
     */
    public NoFlightsAvailableException(String message, String origin, String destination, 
                                      String startDate, String endDate) {
        super(message);
        this.origin = origin;
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
    }

}