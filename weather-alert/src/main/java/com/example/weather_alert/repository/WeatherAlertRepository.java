package com.example.weather_alert.repository;

import com.example.weather_alert.model.WeatherAlert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherAlertRepository extends JpaRepository<WeatherAlert, Long> {
}
