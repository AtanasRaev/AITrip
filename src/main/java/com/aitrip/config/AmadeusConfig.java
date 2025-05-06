package com.aitrip.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
public class AmadeusConfig {
    @Value("${app.amadeus.api.key}")
    private String apiKey;

    @Value("${app.amadeus.api.secret}")
    private String apiSecret;
}
