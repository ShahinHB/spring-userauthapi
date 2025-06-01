package com.example.userauthapi.service;

import com.example.userauthapi.entity.UserEntity;
import com.example.userauthapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;
import com.example.userauthapi.dto.UserDto;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> {
                    UserDto dto = new UserDto();
                    dto.setId(user.getId());
                    dto.setUsername(user.getUsername());
                    dto.setEmail(user.getEmail());
                    dto.setRole(user.getRole());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
