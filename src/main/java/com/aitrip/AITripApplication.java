package com.aitrip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class AITripApplication {

    public static void main(String[] args) {
        SpringApplication.run(AITripApplication.class, args);
    }

}
