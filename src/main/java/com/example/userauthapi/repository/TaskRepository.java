package com.example.userauthapi.repository;

import com.example.userauthapi.entity.TaskEntity;
import com.example.userauthapi.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    List<TaskEntity> findByUser(UserEntity user);
}
