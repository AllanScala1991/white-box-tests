package com.example.tasks.services;

import com.example.tasks.models.TaskModel;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TaskServiceTests {
    private final TaskService taskService = Mockito.mock(TaskService.class);
    @Test
    public void createTaskTest() {
        TaskModel taskModel = new TaskModel();

        Mockito.when(taskService.createTask(taskModel)).thenReturn(taskModel);

        TaskModel result = taskService.createTask(taskModel);

        Assertions.assertEquals(result, taskModel);
        Mockito.verify(taskService).createTask(taskModel);
    }

    @Test
    public void findTaskByNameTest() {
        String name = "Test";
        List<TaskModel> tasks = new ArrayList<>();
        Mockito.when(taskService.findTaskByName(name)).thenReturn(tasks);

        List<TaskModel> result = taskService.findTaskByName(name);

        Assertions.assertEquals(result, tasks);
        Mockito.verify(taskService).findTaskByName(name);
    }

    @Test
    public void findAllTasksTest() {
        List<TaskModel> tasks = new ArrayList<>();
        Mockito.when(taskService.findAllTasks()).thenReturn(tasks);

        List<TaskModel> result = taskService.findAllTasks();

        Assertions.assertEquals(result, tasks);
        Mockito.verify(taskService).findAllTasks();
    }

    @Test
    public void findTaskByID() {
        UUID id = UUID.randomUUID();
        Optional<TaskModel> model = Optional.empty();

        Mockito.when(taskService.findTaskById(id)).thenReturn(model);
        Optional<TaskModel> result = taskService.findTaskById(id);

        Assertions.assertEquals(result, model);
        Mockito.verify(taskService).findTaskById(id);
    }

    @Test
    public void deleteTaskByID() {
        UUID id = UUID.randomUUID();

        Mockito.doNothing().when(taskService).deleteTaskById(id);

        taskService.deleteTaskById(id);

        Mockito.verify(taskService).deleteTaskById(id);
    }

}
