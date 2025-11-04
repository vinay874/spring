package com.pvkr.weather.service;

import com.pvkr.weather.dto.WeatherResponse;
import com.pvkr.weather.model.WeatherData;
import com.pvkr.weather.repository.WeatherDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class WeatherService {
    
    @Autowired
    private WeatherDataRepository weatherDataRepository;
    
    @Value("${weather.api.key}")
    private String apiKey;
    
    @Value("${weather.api.base-url}")
    private String baseUrl;
    
    private final WebClient webClient;
    
    public WeatherService() {
        this.webClient = WebClient.builder().build();
    }
    
    public WeatherResponse getCurrentWeather(String city) {
        // Check if we have cached data
        Optional<WeatherData> cachedData = weatherDataRepository.findByCity(city);
        
        if (cachedData.isPresent()) {
            WeatherData data = cachedData.get();
            return new WeatherResponse(
                data.getCity(),
                data.getTemperature(),
                data.getCondition()
            );
        }
        
        // Fetch from external API
        return fetchWeatherFromAPI(city, false);
    }
    
    public WeatherResponse getDetailedWeather(String city) {
        // Check if we have cached data
        Optional<WeatherData> cachedData = weatherDataRepository.findByCity(city);
        
        if (cachedData.isPresent()) {
            WeatherData data = cachedData.get();
            return new WeatherResponse(
                data.getCity(),
                data.getTemperature(),
                data.getCondition(),
                data.getHumidity(),
                data.getWindSpeed(),
                data.getLastUpdated().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            );
        }
        
        // Fetch from external API
        return fetchWeatherFromAPI(city, true);
    }
    
    public WeatherResponse refreshWeatherData(String city) {
        return fetchWeatherFromAPI(city, true);
    }
    
    private WeatherResponse fetchWeatherFromAPI(String city, boolean includeDetails) {
        try {
            String url = baseUrl + "/weather?q=" + city + "&appid=" + apiKey + "&units=metric";
            
            WeatherApiResponse apiResponse = webClient.get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(WeatherApiResponse.class)
                    .block();
            
            if (apiResponse != null) {
                WeatherData weatherData = new WeatherData(
                    city,
                    apiResponse.main.temp,
                    apiResponse.weather[0].description,
                    apiResponse.main.humidity,
                    apiResponse.wind.speed
                );
                
                // Save or update in database
                weatherDataRepository.findByCity(city)
                    .ifPresent(existing -> weatherData.setId(existing.getId()));
                weatherDataRepository.save(weatherData);
                
                if (includeDetails) {
                    return new WeatherResponse(
                        city,
                        apiResponse.main.temp,
                        apiResponse.weather[0].description,
                        apiResponse.main.humidity,
                        apiResponse.wind.speed,
                        weatherData.getLastUpdated().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                    );
                } else {
                    return new WeatherResponse(
                        city,
                        apiResponse.main.temp,
                        apiResponse.weather[0].description
                    );
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch weather data: " + e.getMessage());
        }
        
        throw new RuntimeException("Failed to fetch weather data");
    }
    
    // Inner classes for API response mapping
    public static class WeatherApiResponse {
        public Main main;
        public Weather[] weather;
        public Wind wind;
    }
    
    public static class Main {
        public double temp;
        public double humidity;
    }
    
    public static class Weather {
        public String description;
    }
    
    public static class Wind {
        public double speed;
    }
}
