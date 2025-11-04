package com.pvkr.weather.dto;

import com.pvkr.weather.model.Role;

public class UserResponse {
    
    private Long id;
    private String username;
    private Role role;
    
    // Constructors
    public UserResponse() {}
    
    public UserResponse(Long id, String username, Role role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public Role getRole() {
        return role;
    }
    
    public void setRole(Role role) {
        this.role = role;
    }
}
