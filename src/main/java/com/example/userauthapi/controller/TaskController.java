package com.example.userauthapi.controller;

import com.example.userauthapi.dto.TaskDto;
import com.example.userauthapi.entity.TaskEntity;
import com.example.userauthapi.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public ResponseEntity<List<TaskEntity>> getMyTasks() {
        return ResponseEntity.ok(taskService.getMyTasks());
    }

    @PostMapping
    public ResponseEntity<TaskEntity> createTask(@RequestBody TaskDto dto) {
        return ResponseEntity.ok(taskService.createTask(dto));
    }
}
