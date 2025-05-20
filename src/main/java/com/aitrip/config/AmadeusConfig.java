package com.aitrip.config;

import com.amadeus.Amadeus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmadeusConfig {
    @Value("${app.amadeus.api.production.key}")
    private String productionApiKey;

    @Value("${app.amadeus.api.production.secret}")
    private String productionApiSecret;

    @Value("${app.amadeus.api.test.key}")
    private String testApiKey;

    @Value("${app.amadeus.api.test.secret}")
    private String testApiSecret;

    @Bean(name = "amadeusProd")
    public Amadeus amadeusProductionClient() {
        return Amadeus.builder(productionApiKey, productionApiSecret)
                .setHostname("production")
                .build();
    }

    @Bean(name = "amadeusTest")
    public Amadeus amadeusTestClient() {
        return Amadeus.builder(testApiKey, testApiSecret)
                .setHostname("test")
                .build();
    }
}
