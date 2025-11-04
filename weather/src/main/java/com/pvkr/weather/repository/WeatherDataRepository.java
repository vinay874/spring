package com.pvkr.weather.repository;

import com.pvkr.weather.model.WeatherData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WeatherDataRepository extends JpaRepository<WeatherData, Long> {
    
    Optional<WeatherData> findByCity(String city);
    
    boolean existsByCity(String city);
}
