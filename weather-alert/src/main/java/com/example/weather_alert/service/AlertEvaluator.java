package com.example.weather_alert.service;

import com.example.weather_alert.client.TomorrowIoClient;
import com.example.weather_alert.model.WeatherAlert;
import com.example.weather_alert.model.WeatherResponse;
import com.example.weather_alert.repository.WeatherAlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AlertEvaluator {

    @Autowired
    private WeatherAlertRepository repository;

    @Autowired
    private TomorrowIoClient weatherClient;

    @Scheduled(fixedRate = 300000) // every 5 minutes
    public void evaluateAlerts() {
        System.out.println("Running scheduled alert check...");

        List<WeatherAlert> alerts = repository.findAll();

        // Cache per-location weather to reduce API calls
        Map<String, WeatherResponse> weatherCache = new HashMap<>();

        for (WeatherAlert alert : alerts) {
            String location = alert.getLocation();

            // Fetch and cache weather per location
            WeatherResponse weather;
            if (weatherCache.containsKey(location)) {
                weather = weatherCache.get(location);
            } else {
                try {
                    System.out.println("Trying to fetch weather from Tomorrow.io...");

                    weather = weatherClient.getWeatherByCity(location);
                    weatherCache.put(location, weather);
                } catch (HttpClientErrorException.TooManyRequests e) {
                    System.err.println("⚠️ Rate limit hit for location: " + location + ". Skipping.");
                    continue;
                } catch (Exception e) {
                    System.err.println("❌ Failed to fetch weather for " + location + ": " + e.getMessage());
                    continue;
                }
            }

            // Get the relevant parameter value
            double value = switch (alert.getParameter()) {
                case "temperature" -> weather.getTemperature();
                case "humidity" -> weather.getHumidity();
                case "windSpeed" -> weather.getWindSpeed();
                default -> Double.NaN;
            };

            System.out.println("Evaluating alert: " + alert.getDescription() + " for " + location);
            System.out.println("Current " + alert.getParameter() + ": " + value);

            // Evaluate condition
            boolean isTriggered = switch (alert.getOperator()) {
                case ">" -> value > alert.getThreshold();
                case "<" -> value < alert.getThreshold();
                case "=" -> value == alert.getThreshold();
                default -> false;
            };

            if (isTriggered) {
                System.out.println("🚨 ALERT TRIGGERED: " + alert.getDescription());
            } else {
                System.out.println("✅ All Clear");
            }

            alert.setTriggered(isTriggered);
            repository.save(alert);
        }
    }
}
