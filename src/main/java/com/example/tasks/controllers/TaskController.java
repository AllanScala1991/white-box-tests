package com.example.tasks.controllers;

import com.example.tasks.dtos.TaskDTO;
import com.example.tasks.models.TaskModel;
import com.example.tasks.services.TaskService;
import jakarta.validation.Valid;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(value = "", maxAge = 3600)
@RequestMapping("/task")
public class TaskController {
    @Autowired
    TaskService taskService;

    @GetMapping("/health")
    public ResponseEntity<Object> health() {
        return ResponseEntity.status(HttpStatus.OK).body("Task Service is online.");
    }

    @PostMapping()
    public ResponseEntity<Object> create(@RequestBody @Valid TaskDTO taskDTO) {
        TaskModel taskModel = new TaskModel();
        BeanUtils.copyProperties(taskDTO, taskModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.createTask(taskModel));
    }

    @GetMapping("")
    public ResponseEntity<Object> findTask(@RequestParam(value = "name", required = false) String name) {
        if(name == null) {
            List<TaskModel> tasks = taskService.findAllTasks();
            if(tasks.isEmpty()) {
                JSONObject response = new JSONObject();
                response.put("message", "Nenhuma tarefa localizada.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response.toString());
            }

            return ResponseEntity.status(HttpStatus.OK).body(tasks);
        }

        List<TaskModel> tasks = taskService.findTaskByName(name);

        if(tasks.isEmpty()) {
            JSONObject response = new JSONObject();
            response.put("message", "Nenhuma tarefa localizada com esse nome.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response.toString());
        }

        return ResponseEntity.status(HttpStatus.OK).body(tasks);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateTask(@PathVariable(value = "id") UUID id, @RequestBody @Valid TaskDTO taskDTO) {
        Optional<TaskModel> task = taskService.findTaskById(id);

        if(task.isEmpty()) {
            JSONObject response = new JSONObject();
            response.put("message", "ID inválido");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.toString());
        }

        TaskModel taskModel = new TaskModel();
        BeanUtils.copyProperties(taskDTO, taskModel);
        taskModel.setId(task.get().getId());

        return ResponseEntity.status(HttpStatus.OK).body(taskService.createTask(taskModel));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTask(@PathVariable(value = "id") UUID id) {
        Optional<TaskModel> task = taskService.findTaskById(id);

        if(task.isEmpty()) {
            JSONObject response = new JSONObject();
            response.put("message", "ID inválido");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.toString());
        }

        taskService.deleteTaskById(id);

        JSONObject response = new JSONObject();
        response.put("message", "Tarefa deletada com sucesso!");

        return ResponseEntity.status(HttpStatus.OK).body(response.toString());
    }
}
