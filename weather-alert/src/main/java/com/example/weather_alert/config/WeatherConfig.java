package com.example.weather_alert.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WeatherConfig {

    @Value("${tomorrow.api.key}")
    private String apiKey;

    public String getApiKey() {
        return apiKey;
    }
}

