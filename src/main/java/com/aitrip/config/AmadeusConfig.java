package com.aitrip.config;

import com.aitrip.exception.external.amadeus.InvalidAmadeusEnvironmentException;
import com.aitrip.exception.external.amadeus.MissingAmadeusConfigurationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmadeusConfig {
    @Value("${app.amadeus.api.test.key}")
    private String testApiKey;

    @Value("${app.amadeus.api.test.secret}")
    private String testApiSecret;

    @Value("${app.amadeus.api.test.url}")
    private String testUrl;

    @Value("${app.amadeus.api.production.key}")
    private String productionApiKey;

    @Value("${app.amadeus.api.production.secret}")
    private String productionApiSecret;

    @Value("${app.amadeus.api.production.url}")
    private String productionUrl;

    private static final AmadeusEnvironment[] VALID_ENVIRONMENTS = {
        AmadeusEnvironment.TEST, 
        AmadeusEnvironment.PRODUCTION
    };

    /**
     * Gets the Amadeus URL for the specified environment.
     *
     * @param environment the environment
     * @return the Amadeus URL
     * @throws InvalidAmadeusEnvironmentException if the environment is invalid
     * @throws MissingAmadeusConfigurationException if the URL configuration is missing
     */
    public String getAmadeusUrl(AmadeusEnvironment environment) {
        String url;

        switch (environment) {
            case TEST:
                url = this.testUrl;
                if (url == null || url.isEmpty()) {
                    throw new MissingAmadeusConfigurationException(
                        "Missing URL configuration for test environment",
                        environment.getValue(),
                        "app.amadeus.api.test.url"
                    );
                }
                break;
            case PRODUCTION:
                url = this.productionUrl;
                if (url == null || url.isEmpty()) {
                    throw new MissingAmadeusConfigurationException(
                        "Missing URL configuration for production environment",
                        environment.getValue(),
                        "app.amadeus.api.production.url"
                    );
                }
                break;
            default:
                throw new InvalidAmadeusEnvironmentException(
                    "Invalid environment: " + environment,
                    environment.getValue(),
                    getValidEnvironmentStrings()
                );
        }

        return url;
    }


    /**
     * Gets the Amadeus API key for the specified environment.
     *
     * @param environment the environment
     * @return the Amadeus API key
     * @throws InvalidAmadeusEnvironmentException if the environment is invalid
     * @throws MissingAmadeusConfigurationException if the API key configuration is missing
     */
    public String getAmadeusApiKey(AmadeusEnvironment environment) {
        String apiKey;

        switch (environment) {
            case TEST:
                apiKey = this.testApiKey;
                if (apiKey == null || apiKey.isEmpty()) {
                    throw new MissingAmadeusConfigurationException(
                        "Missing API key configuration for test environment",
                        environment.getValue(),
                        "app.amadeus.api.test.key"
                    );
                }
                break;
            case PRODUCTION:
                apiKey = this.productionApiKey;
                if (apiKey == null || apiKey.isEmpty()) {
                    throw new MissingAmadeusConfigurationException(
                        "Missing API key configuration for production environment",
                        environment.getValue(),
                        "app.amadeus.api.production.key"
                    );
                }
                break;
            default:
                throw new InvalidAmadeusEnvironmentException(
                    "Invalid environment: " + environment,
                    environment.getValue(),
                    getValidEnvironmentStrings()
                );
        }

        return apiKey;
    }


    /**
     * Gets the Amadeus API secret for the specified environment.
     *
     * @param environment the environment
     * @return the Amadeus API secret
     * @throws InvalidAmadeusEnvironmentException if the environment is invalid
     * @throws MissingAmadeusConfigurationException if the API secret configuration is missing
     */
    public String getAmadeusApiSecret(AmadeusEnvironment environment) {
        String apiSecret;

        switch (environment) {
            case TEST:
                apiSecret = this.testApiSecret;
                if (apiSecret == null || apiSecret.isEmpty()) {
                    throw new MissingAmadeusConfigurationException(
                        "Missing API secret configuration for test environment",
                        environment.getValue(),
                        "app.amadeus.api.test.secret"
                    );
                }
                break;
            case PRODUCTION:
                apiSecret = this.productionApiSecret;
                if (apiSecret == null || apiSecret.isEmpty()) {
                    throw new MissingAmadeusConfigurationException(
                        "Missing API secret configuration for production environment",
                        environment.getValue(),
                        "app.amadeus.api.production.secret"
                    );
                }
                break;
            default:
                throw new InvalidAmadeusEnvironmentException(
                    "Invalid environment: " + environment,
                    environment.getValue(),
                    getValidEnvironmentStrings()
                );
        }

        return apiSecret;
    }



    /**
     * Gets the valid environment strings.
     *
     * @return the valid environment strings
     */
    private String[] getValidEnvironmentStrings() {
        String[] validEnvironmentStrings = new String[VALID_ENVIRONMENTS.length];
        for (int i = 0; i < VALID_ENVIRONMENTS.length; i++) {
            validEnvironmentStrings[i] = VALID_ENVIRONMENTS[i].getValue();
        }
        return validEnvironmentStrings;
    }
}
