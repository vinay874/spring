package com.pvkr.weather.dto;

public class WeatherResponse {
    
    private String city;
    private Double temperature;
    private String condition;
    private Double humidity;
    private Double windSpeed;
    private String lastUpdated;
    
    // Constructors
    public WeatherResponse() {}
    
    public WeatherResponse(String city, Double temperature, String condition) {
        this.city = city;
        this.temperature = temperature;
        this.condition = condition;
    }
    
    public WeatherResponse(String city, Double temperature, String condition, Double humidity, Double windSpeed, String lastUpdated) {
        this.city = city;
        this.temperature = temperature;
        this.condition = condition;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.lastUpdated = lastUpdated;
    }
    
    // Getters and Setters
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
    
    public String getLastUpdated() {
        return lastUpdated;
    }
    
    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
