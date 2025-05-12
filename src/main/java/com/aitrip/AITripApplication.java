package com.aitrip;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class AITripApplication {
    @PostConstruct
    public void showPort() {
        System.out.println("📢 PORT = " + System.getenv("PORT"));
    }

    public static void main(String[] args) {
        SpringApplication.run(AITripApplication.class, args);
    }
}
