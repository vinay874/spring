package com.pvkr.weather.controller;

import com.pvkr.weather.dto.AuthResponse;
import com.pvkr.weather.dto.LoginRequest;
import com.pvkr.weather.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {
    
    @Mock
    private AuthenticationManager authenticationManager;
    
    @Mock
    private JwtUtil jwtUtil;
    
    @InjectMocks
    private AuthController authController;
    
    private LoginRequest validLoginRequest;
    private LoginRequest invalidLoginRequest;
    private UserDetails userDetails;
    private Authentication authentication;
    
    @BeforeEach
    void setUp() {
        validLoginRequest = new LoginRequest();
        validLoginRequest.setUsername("testuser");
        validLoginRequest.setPassword("password123");
        
        invalidLoginRequest = new LoginRequest();
        invalidLoginRequest.setUsername("testuser");
        invalidLoginRequest.setPassword("wrongpassword");
        
        userDetails = new User("testuser", "password123", Collections.emptyList());
        authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
    
    @Test
    void testLogin_Success() {
        // Given
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
            .thenReturn(authentication);
        when(jwtUtil.generateToken(userDetails)).thenReturn("jwt-token");
        
        // When
        ResponseEntity<AuthResponse> response = authController.login(validLoginRequest);
        
        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("jwt-token", response.getBody().getToken());
        assertNull(response.getBody().getMessage());
        
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtUtil).generateToken(userDetails);
    }
    
    @Test
    void testLogin_InvalidCredentials() {
        // Given
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
            .thenThrow(new BadCredentialsException("Invalid credentials"));
        
        // When
        ResponseEntity<AuthResponse> response = authController.login(invalidLoginRequest);
        
        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNull(response.getBody().getToken());
        assertEquals("Invalid username or password", response.getBody().getMessage());
        
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtUtil, never()).generateToken(any(UserDetails.class));
    }
    
    @Test
    void testLogin_AuthenticationException() {
        // Given
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
            .thenThrow(new RuntimeException("Authentication failed"));
        
        // When
        ResponseEntity<AuthResponse> response = authController.login(validLoginRequest);
        
        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNull(response.getBody().getToken());
        assertEquals("Invalid username or password", response.getBody().getMessage());
        
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtUtil, never()).generateToken(any(UserDetails.class));
    }
}
