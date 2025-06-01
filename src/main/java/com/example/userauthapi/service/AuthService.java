package com.example.userauthapi.service;

import com.example.userauthapi.dto.RegisterRequest;
import com.example.userauthapi.dto.LoginRequest;
import com.example.userauthapi.entity.UserEntity;
import com.example.userauthapi.exception.UnauthorizedException;
import com.example.userauthapi.repository.UserRepository;
import com.example.userauthapi.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public String register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            return "Username already taken";
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            return "Email already registered";
        }

        UserEntity user = UserEntity.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role("USER")
                .build();

        userRepository.save(user);

        return "User registered successfully";
    }

    public String login(LoginRequest request) {
        UserEntity user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("Invalid username or password");
        }

        return jwtUtil.generateToken(user.getUsername());
    }
}
