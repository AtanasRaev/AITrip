package com.aitrip.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestConfig {
    @Bean
    public RestClient genericRestClient() {
        return RestClient.create();
    }
}
