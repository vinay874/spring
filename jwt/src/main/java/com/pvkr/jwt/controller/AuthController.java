package com.pvkr.jwt.controller;

import com.pvkr.jwt.dto.AuthRequest;
import com.pvkr.jwt.dto.AuthResponse;
import com.pvkr.jwt.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest authRequest){
        Authentication authentication = new UsernamePasswordAuthenticationToken(authRequest.getUsername(),authRequest.getPassword());
        authenticationManager.authenticate(authentication);
        AuthResponse response = new AuthResponse();
        response.setSuccess(true);
        response.setToken(jwtUtil.generateJwtToken(authRequest.getUsername()));
        return response;
    }
}
