package com.example.weather_alert.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class WeatherAlert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String location;     // City or coordinates
    private String parameter;    // "temperature", "humidity", "windSpeed"
    private String operator;     // ">", "<", "="
    private Double threshold;
    private String description;
    private Boolean triggered = false;

    public String getLocation() {
        return location;
    }

    public Long getId() {
        return id;
    }

    public String getParameter() {
        return parameter;
    }

    public String getOperator() {
        return operator;
    }

    public Double getThreshold() {
        return threshold;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getTriggered() {
        return triggered;
    }

    public void setTriggered(Boolean triggered) {
        this.triggered = triggered;
    }
}
