package com.pvkr.weather.controller;

import com.pvkr.weather.dto.MessageResponse;
import com.pvkr.weather.dto.RegisterRequest;
import com.pvkr.weather.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public")
@Tag(name = "Public", description = "Public endpoints")
public class PublicController {
    
    @Autowired
    private UserService userService;
    
    @PostMapping("/register")
    @Operation(summary = "User registration", description = "Register a new user account")
    public ResponseEntity<MessageResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        try {
            userService.registerUser(registerRequest.getUsername(), registerRequest.getPassword());
            return ResponseEntity.ok(new MessageResponse("User registered successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(new MessageResponse("Registration failed: " + e.getMessage()));
        }
    }
}
