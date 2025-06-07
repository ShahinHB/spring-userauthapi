package com.example.userauthapi.service;

import com.example.userauthapi.entity.TaskEntity;
import com.example.userauthapi.entity.UserEntity;
import com.example.userauthapi.repository.TaskRepository;
import com.example.userauthapi.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.TestingAuthenticationToken;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TaskService taskService;

    public TaskServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getMyTasks_successful() {
        // Simulate logged in user
        SecurityContextHolder.getContext().setAuthentication(
                new TestingAuthenticationToken("testuser", null)
        );

        UserEntity user = new UserEntity();
        user.setUsername("testuser");

        TaskEntity task = new TaskEntity();
        task.setTitle("Test Task");

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(taskRepository.findByUser(user)).thenReturn(List.of(task));

        List<TaskEntity> tasks = taskService.getMyTasks();

        assertNotNull(tasks);
        assertEquals(1, tasks.size());
        assertEquals("Test Task", tasks.get(0).getTitle());

        verify(userRepository).findByUsername("testuser");
        verify(taskRepository).findByUser(user);
    }
}
