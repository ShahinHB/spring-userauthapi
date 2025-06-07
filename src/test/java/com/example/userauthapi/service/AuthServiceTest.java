package com.example.userauthapi.service;

import com.example.userauthapi.dto.LoginRequest;
import com.example.userauthapi.entity.UserEntity;
import com.example.userauthapi.repository.UserRepository;
import com.example.userauthapi.security.JwtUtil;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;  // <--- BURAYA DİKKAT

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthService authService;

    public AuthServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void login_successful() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("testuser");
        loginRequest.setPassword("password123");

        UserEntity user = new UserEntity();
        user.setUsername("testuser");
        user.setPassword("hashedpassword");

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("password123", "hashedpassword")).thenReturn(true);
        when(jwtUtil.generateToken("testuser")).thenReturn("mocked-jwt-token");

        String token = authService.login(loginRequest);

        assertNotNull(token);
        assertEquals("mocked-jwt-token", token);

        verify(userRepository).findByUsername("testuser");
        verify(passwordEncoder).matches("password123", "hashedpassword");
        verify(jwtUtil).generateToken("testuser");
    }
}
