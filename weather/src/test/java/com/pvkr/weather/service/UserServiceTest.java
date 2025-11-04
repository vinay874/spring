package com.pvkr.weather.service;

import com.pvkr.weather.dto.CreateUserRequest;
import com.pvkr.weather.model.Role;
import com.pvkr.weather.model.User;
import com.pvkr.weather.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    
    @Mock
    private UserRepository userRepository;
    
    @Mock
    private PasswordEncoder passwordEncoder;
    
    @InjectMocks
    private UserService userService;
    
    private User testUser;
    private CreateUserRequest createUserRequest;
    
    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testuser");
        testUser.setPassword("encodedPassword");
        testUser.setRole(Role.USER);
        
        createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("newuser");
        createUserRequest.setPassword("password123");
        createUserRequest.setRole(Role.USER);
    }
    
    @Test
    void testRegisterUser_Success() {
        // Given
        when(userRepository.existsByUsername("testuser")).thenReturn(false);
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        
        // When
        User result = userService.registerUser("testuser", "password123");
        
        // Then
        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        assertEquals(Role.USER, result.getRole());
        verify(userRepository).existsByUsername("testuser");
        verify(passwordEncoder).encode("password123");
        verify(userRepository).save(any(User.class));
    }
    
    @Test
    void testRegisterUser_UsernameAlreadyExists() {
        // Given
        when(userRepository.existsByUsername("testuser")).thenReturn(true);
        
        // When & Then
        assertThrows(RuntimeException.class, () -> {
            userService.registerUser("testuser", "password123");
        });
        
        verify(userRepository).existsByUsername("testuser");
        verify(userRepository, never()).save(any(User.class));
    }
    
    @Test
    void testCreateUser_Success() {
        // Given
        when(userRepository.existsByUsername("newuser")).thenReturn(false);
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        
        // When
        User result = userService.createUser(createUserRequest);
        
        // Then
        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        verify(userRepository).existsByUsername("newuser");
        verify(passwordEncoder).encode("password123");
        verify(userRepository).save(any(User.class));
    }
    
    @Test
    void testGetAllUsers() {
        // Given
        User user1 = new User();
        user1.setId(1L);
        user1.setUsername("user1");
        user1.setRole(Role.USER);
        
        User user2 = new User();
        user2.setId(2L);
        user2.setUsername("user2");
        user2.setRole(Role.ADMIN);
        
        List<User> users = Arrays.asList(user1, user2);
        when(userRepository.findAll()).thenReturn(users);
        
        // When
        List<com.pvkr.weather.dto.UserResponse> result = userService.getAllUsers();
        
        // Then
        assertEquals(2, result.size());
        assertEquals("user1", result.get(0).getUsername());
        assertEquals(Role.USER, result.get(0).getRole());
        assertEquals("user2", result.get(1).getUsername());
        assertEquals(Role.ADMIN, result.get(1).getRole());
    }
    
    @Test
    void testLoadUserByUsername_Success() {
        // Given
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));
        
        // When
        org.springframework.security.core.userdetails.UserDetails result = 
            userService.loadUserByUsername("testuser");
        
        // Then
        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        assertEquals("encodedPassword", result.getPassword());
        assertTrue(result.getAuthorities().stream()
            .anyMatch(auth -> auth.getAuthority().equals("ROLE_USER")));
    }
    
    @Test
    void testLoadUserByUsername_UserNotFound() {
        // Given
        when(userRepository.findByUsername("nonexistent")).thenReturn(Optional.empty());
        
        // When & Then
        assertThrows(org.springframework.security.core.userdetails.UsernameNotFoundException.class, () -> {
            userService.loadUserByUsername("nonexistent");
        });
    }
}
