package com.aitrip.config;

/**
 * Enum representing the available Amadeus API environments.
 */
public enum AmadeusEnvironment {
    /**
     * Test environment for development and testing purposes.
     */
    TEST,
    
    /**
     * Production environment for live applications.
     */
    PRODUCTION;
    
    /**
     * Returns the lowercase string representation of the environment.
     * This is useful for compatibility with configuration properties.
     *
     * @return the lowercase string representation
     */
    public String getValue() {
        return this.name().toLowerCase();
    }
    
    /**
     * Converts a string to the corresponding AmadeusEnvironment enum value.
     * 
     * @param value the string value to convert
     * @return the corresponding AmadeusEnvironment enum value
     * @throws IllegalArgumentException if the value doesn't match any environment
     */
    public static AmadeusEnvironment fromString(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Environment value cannot be null");
        }
        
        try {
            return AmadeusEnvironment.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid environment: " + value + 
                ". Valid environments are: " + TEST.getValue() + ", " + PRODUCTION.getValue());
        }
    }
}