package com.example.weather_alert.model;

public class WeatherResponse {
    private final double temperature;
    private final double humidity;
    private final double windSpeed;

    public WeatherResponse(double temperature, double humidity, double windSpeed) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }
}
