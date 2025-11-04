package com.pvkr.weather.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "weather_data")
public class WeatherData {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "City is required")
    @Column(nullable = false)
    private String city;
    
    @NotNull(message = "Temperature is required")
    @Column(nullable = false)
    private Double temperature;
    
    @NotBlank(message = "Condition is required")
    @Column(nullable = false)
    private String condition;
    
    @NotNull(message = "Humidity is required")
    @Column(nullable = false)
    private Double humidity;
    
    @NotNull(message = "Wind speed is required")
    @Column(nullable = false)
    private Double windSpeed;
    
    @Column(nullable = false)
    private LocalDateTime lastUpdated;
    
    // Constructors
    public WeatherData() {
        this.lastUpdated = LocalDateTime.now();
    }
    
    public WeatherData(String city, Double temperature, String condition, Double humidity, Double windSpeed) {
        this.city = city;
        this.temperature = temperature;
        this.condition = condition;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.lastUpdated = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getCity() {
        return city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    public Double getTemperature() {
        return temperature;
    }
    
    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }
    
    public String getCondition() {
        return condition;
    }
    
    public void setCondition(String condition) {
        this.condition = condition;
    }
    
    public Double getHumidity() {
        return humidity;
    }
    
    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }
    
    public Double getWindSpeed() {
        return windSpeed;
    }
    
    public void setWindSpeed(Double windSpeed) {
        this.windSpeed = windSpeed;
    }
    
    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }
    
    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
