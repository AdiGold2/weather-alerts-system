package com.example.weather_alert.controller;

import com.example.weather_alert.model.WeatherAlert;
import com.example.weather_alert.repository.WeatherAlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alerts")
public class AlertController {

    @Autowired
    private WeatherAlertRepository repository;

    @PostMapping
    public WeatherAlert createAlert(@RequestBody WeatherAlert alert) {
        return repository.save(alert);
    }

    @GetMapping
    public List<WeatherAlert> getAllAlerts() {
        return repository.findAll();
    }
}
