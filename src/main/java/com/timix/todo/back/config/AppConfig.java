package com.timix.todo.back.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;



@Configuration
public class AppConfig {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @PostConstruct
    public void validateConfiguration() {
        if (jwtSecret == null || jwtSecret.trim().isEmpty()) {
            throw new IllegalStateException("JWT secret key must be set in application.properties");
        }
    }
}
