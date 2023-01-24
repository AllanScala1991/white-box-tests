package com.example.tasks.services;

import com.example.tasks.models.TaskModel;
import com.example.tasks.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskService {
    @Autowired
    TaskRepository taskRepository;

    //create
    public TaskModel createTask(TaskModel task) {
        return taskRepository.save(task);
    }

    //findByName
    public List<TaskModel> findTaskByName(String name) {
        return taskRepository.findByName(name);
    }

    //findAll
    public List<TaskModel> findAllTasks() {
        return taskRepository.findAll();
    }

    //findByID
    public Optional<TaskModel> findTaskById(UUID id) {
        return taskRepository.findById(id);
    }

    //delete
    public void deleteTaskById(UUID id) {
        taskRepository.deleteById(id);
    }
}
