package com.aitrip.exception.external.amadeus;

import lombok.Getter;

/**
 * Exception thrown when required Amadeus configuration values are missing.
 */
@Getter
public class MissingAmadeusConfigurationException extends RuntimeException {
    
    /**
     * -- GETTER --
     * Gets the environment for which configuration is missing.
     *
     * @return the environment
     */
    private final String environment;
    
    /**
     * -- GETTER --
     * Gets the configuration property that is missing.
     *
     * @return the missing property
     */
    private final String missingProperty;
    
    /**
     * Constructs a new MissingAmadeusConfigurationException with the specified detail message.
     * 
     * @param message the detail message
     * @param environment the environment for which configuration is missing
     * @param missingProperty the configuration property that is missing
     */
    public MissingAmadeusConfigurationException(String message, String environment, String missingProperty) {
        super(message);
        this.environment = environment;
        this.missingProperty = missingProperty;
    }
}