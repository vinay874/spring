package com.pvkr.weather.controller;

import com.pvkr.weather.dto.CreateUserRequest;
import com.pvkr.weather.dto.MessageResponse;
import com.pvkr.weather.dto.UserResponse;
import com.pvkr.weather.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@Tag(name = "Admin", description = "Admin endpoints")
public class AdminController {
    
    @Autowired
    private UserService userService;
    
    @PostMapping("/create-user")
    @Operation(summary = "Create user", description = "Create a new user or admin account")
    public ResponseEntity<MessageResponse> createUser(@Valid @RequestBody CreateUserRequest createUserRequest) {
        try {
            userService.createUser(createUserRequest);
            return ResponseEntity.ok(new MessageResponse("User created successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(new MessageResponse("Failed to create user: " + e.getMessage()));
        }
    }
    
    @GetMapping("/users")
    @Operation(summary = "Get all users", description = "Retrieve a list of all registered users")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        try {
            List<UserResponse> users = userService.getAllUsers();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
