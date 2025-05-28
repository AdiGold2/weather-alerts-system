package com.example.weather_alert.client;

import com.example.weather_alert.config.WeatherConfig;
import com.example.weather_alert.model.WeatherResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class TomorrowIoClient {

    private static final String BASE_URL = "https://api.tomorrow.io/v4/weather/realtime";

    @Autowired
    private WeatherConfig config;

    private final RestTemplate restTemplate = new RestTemplate();

    public WeatherResponse getWeatherByCity(String city) {
        String url = BASE_URL + "?location=" + city + "&apikey=" + config.getApiKey();
        String response = restTemplate.getForObject(url, String.class);

        try {
            // Create ObjectMapper instance
            ObjectMapper objectMapper = new ObjectMapper();

            // Parse JSON string to JsonNode
            JsonNode rootNode = objectMapper.readTree(response);

            // Navigate through the JSON structure
            JsonNode valuesNode = rootNode.path("data").path("values");

            // Extract values
            double temp = valuesNode.path("temperature").asDouble();
            double humidity = valuesNode.path("humidity").asDouble();
            double wind = valuesNode.path("windSpeed").asDouble();

            return new WeatherResponse(temp, humidity, wind);

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse weather response", e);
        }
    }

}
