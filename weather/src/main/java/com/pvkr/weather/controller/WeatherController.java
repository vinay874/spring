package com.pvkr.weather.controller;

import com.pvkr.weather.dto.MessageResponse;
import com.pvkr.weather.dto.WeatherResponse;
import com.pvkr.weather.service.WeatherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/weather")
@Tag(name = "Weather", description = "Weather endpoints")
public class WeatherController {
    
    @Autowired
    private WeatherService weatherService;
    
    @GetMapping("/public/current")
    @Operation(summary = "Get current weather", description = "Get basic weather information for a city")
    public ResponseEntity<WeatherResponse> getCurrentWeather(
            @Parameter(description = "City name") @RequestParam String city) {
        try {
            WeatherResponse weather = weatherService.getCurrentWeather(city);
            return ResponseEntity.ok(weather);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(new WeatherResponse(city, null, "Error: " + e.getMessage()));
        }
    }
    
    @GetMapping("/admin/details")
    @Operation(summary = "Get detailed weather", description = "Get detailed weather information for a city (Admin only)")
    public ResponseEntity<WeatherResponse> getDetailedWeather(
            @Parameter(description = "City name") @RequestParam String city) {
        try {
            WeatherResponse weather = weatherService.getDetailedWeather(city);
            return ResponseEntity.ok(weather);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(new WeatherResponse(city, null, "Error: " + e.getMessage()));
        }
    }
    
    @PostMapping("/admin/refresh")
    @Operation(summary = "Refresh weather data", description = "Refresh cached weather data from external API (Admin only)")
    public ResponseEntity<MessageResponse> refreshWeatherData(
            @Parameter(description = "City name") @RequestParam String city) {
        try {
            weatherService.refreshWeatherData(city);
            return ResponseEntity.ok(new MessageResponse("Weather data refreshed successfully for " + city));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(new MessageResponse("Failed to refresh weather data: " + e.getMessage()));
        }
    }
}
