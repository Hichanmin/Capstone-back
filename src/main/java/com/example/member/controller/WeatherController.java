package com.example.member.controller;

import com.example.member.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;

@RestController
public class WeatherController {
    private final WeatherService weatherService;

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/weather")
    public String getWeather() {
        try {
            weatherService.getWeatherInfo();
            return "Weather API response logged successfully!";
        } catch (IOException e) {
            e.printStackTrace();
            return "Error occurred while logging weather API response!";
        }
    }
}
