package com.aitrip.exception.external.amadeus;

import lombok.Getter;

/**
 * Exception thrown when an invalid environment is specified for Amadeus API.
 */
@Getter
public class InvalidAmadeusEnvironmentException extends RuntimeException {
    
    /**
     * -- GETTER --
     * Gets the invalid environment that was specified.
     *
     * @return the invalid environment
     */
    private final String environment;
    
    /**
     * -- GETTER --
     * Gets the valid environments that are supported.
     *
     * @return the valid environments
     */
    private final String[] validEnvironments;
    
    /**
     * Constructs a new InvalidAmadeusEnvironmentException with the specified detail message and environment.
     * 
     * @param message the detail message
     * @param environment the invalid environment that was specified
     * @param validEnvironments the valid environments that are supported
     */
    public InvalidAmadeusEnvironmentException(String message, String environment, String[] validEnvironments) {
        super(message);
        this.environment = environment;
        this.validEnvironments = validEnvironments;
    }
}