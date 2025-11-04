package com.pvkr.weather;

import com.pvkr.weather.dto.LoginRequest;
import com.pvkr.weather.dto.RegisterRequest;
import com.pvkr.weather.model.Role;
import com.pvkr.weather.repository.UserRepository;
import com.pvkr.weather.repository.WeatherDataRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebMvc
@ActiveProfiles("test")
class WeatherApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WeatherDataRepository weatherDataRepository;

    private String getBaseUrl() {
        return "http://localhost:" + port;
    }

    @Test
    void contextLoads() {
        // This test ensures that the Spring context loads successfully
        assertNotNull(restTemplate);
        assertNotNull(userRepository);
        assertNotNull(weatherDataRepository);
    }

    @Test
    void testUserRegistration() {
        // Given
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("testuser");
        registerRequest.setPassword("password123");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<RegisterRequest> request = new HttpEntity<>(registerRequest, headers);

        // When
        ResponseEntity<String> response = restTemplate.postForEntity(
            getBaseUrl() + "/api/public/register", request, String.class);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("User registered successfully"));
    }

    @Test
    void testUserLogin() {
        // First register a user
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("logintest");
        registerRequest.setPassword("password123");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<RegisterRequest> registerRequestEntity = new HttpEntity<>(registerRequest, headers);

        restTemplate.postForEntity(getBaseUrl() + "/api/public/register", registerRequestEntity, String.class);

        // Then login
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("logintest");
        loginRequest.setPassword("password123");

        HttpEntity<LoginRequest> loginRequestEntity = new HttpEntity<>(loginRequest, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
            getBaseUrl() + "/api/auth/login", loginRequestEntity, String.class);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("token"));
    }

    @Test
    void testPublicWeatherEndpoint() {
        // When
        ResponseEntity<String> response = restTemplate.getForEntity(
            getBaseUrl() + "/api/weather/public/current?city=London", String.class);

        // Then
        // This might fail due to missing API key, but the endpoint should be accessible
        assertTrue(response.getStatusCode() == HttpStatus.OK || 
                  response.getStatusCode() == HttpStatus.BAD_REQUEST);
    }

    @Test
    void testSwaggerUI() {
        // When
        ResponseEntity<String> response = restTemplate.getForEntity(
            getBaseUrl() + "/swagger-ui.html", String.class);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}