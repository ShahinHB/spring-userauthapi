package com.example.userauthapi.service;

import com.example.userauthapi.dto.TaskDto;
import com.example.userauthapi.entity.TaskEntity;
import com.example.userauthapi.entity.UserEntity;
import com.example.userauthapi.repository.TaskRepository;
import com.example.userauthapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public List<TaskEntity> getMyTasks() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findByUsername(username).orElseThrow();
        return taskRepository.findByUser(user);
    }

    public TaskEntity createTask(TaskDto dto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findByUsername(username).orElseThrow();

        TaskEntity task = TaskEntity.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .completed(false)
                .createdAt(LocalDateTime.now())
                .user(user)
                .build();

        return taskRepository.save(task);
    }
}
