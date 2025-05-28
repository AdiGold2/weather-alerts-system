package com.example.weather_alert.controller;

import com.example.weather_alert.client.TomorrowIoClient;
import com.example.weather_alert.model.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    @Autowired
    private TomorrowIoClient client;

    @GetMapping
    public WeatherResponse getWeather(@RequestParam String city) {
        return client.getWeatherByCity(city);
    }
}
