package com.example.tasks.repositories;

import com.example.tasks.models.TaskModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TaskRepository  extends JpaRepository<TaskModel, UUID> {
    List<TaskModel> findByName(String name);
}
