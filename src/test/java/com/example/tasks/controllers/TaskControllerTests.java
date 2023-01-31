package com.example.tasks.controllers;

import com.example.tasks.models.TaskModel;
import com.example.tasks.services.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
// seta qual controller está sendo testado
@WebMvcTest(controllers = TaskController.class)
public class TaskControllerTests {
    // inicializa o mock mvc
    @Autowired
    private MockMvc mockMvc;

    //mocka o service
    @MockBean
    private TaskService taskService;

    @Test
    public void shouldValidateServiceHealth() throws Exception {
        // faz um get atraves do perform do mock mvc
        mockMvc.perform(MockMvcRequestBuilders.get("/task/health"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Task Service is online."));
    }

    @Test
    public void createNewTaskSuccessfully() throws Exception {
        // modelo para criar uma nova task
        TaskModel task = new TaskModel();
        task.setId(UUID.randomUUID());
        task.setName("Test");
        task.setDescription("Test description");
        task.setPriority("Low");

        // converte o model (Java Object) em JSON
        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(task);

        // mocka o retorno do metodo create task do service
        when(taskService.createTask(any(TaskModel.class))).thenReturn(task);

        // realiza um POST verificando o status e o retorno.
        mockMvc.perform(MockMvcRequestBuilders.post("/task")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(payload))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string(payload));
    }

    @Test
    public void createNewTaskWithInvalidPayload() throws Exception {
        TaskModel task = new TaskModel();
        task.setDescription("Test description");
        task.setPriority("Low");

        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(task);

        when(taskService.createTask(any(TaskModel.class))).thenReturn(task);

        mockMvc.perform(MockMvcRequestBuilders.post("/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void findAllTasksSuccessfully() throws Exception {
        TaskModel task = new TaskModel();
        task.setId(UUID.randomUUID());
        task.setName("Test");
        task.setDescription("Test description");
        task.setPriority("Low");

        List<TaskModel> tasks = new ArrayList<>();
        tasks.add(task);

        when(taskService.findAllTasks()).thenReturn(tasks);

        mockMvc.perform(MockMvcRequestBuilders.get("/task"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(new ObjectMapper().writeValueAsString(tasks)));
    }

    @Test
    public void findAllTasksWithEmptyResponse() throws Exception {
        List<TaskModel> tasks = new ArrayList<>();


        when(taskService.findAllTasks()).thenReturn(tasks);

        mockMvc.perform(MockMvcRequestBuilders.get("/task"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("{\"message\":\"Nenhuma tarefa localizada.\"}"));
    }

    @Test
    public void findTaskWithName() throws Exception {
        TaskModel task = new TaskModel();
        task.setId(UUID.randomUUID());
        task.setName("Test");
        task.setDescription("Test description");
        task.setPriority("Low");

        List<TaskModel> tasks = new ArrayList<>();
        tasks.add(task);

        when(taskService.findTaskByName(any(String.class))).thenReturn(tasks);

        mockMvc.perform(MockMvcRequestBuilders.get("/task?name=Test"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(new ObjectMapper().writeValueAsString(tasks)));
    }

    @Test
    public void findTaskWithInvalidName() throws Exception {
        List<TaskModel> tasks = new ArrayList<>();

        when(taskService.findTaskByName(any(String.class))).thenReturn(tasks);

        mockMvc.perform(MockMvcRequestBuilders.get("/task?name=Test"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("{\"message\":\"Nenhuma tarefa localizada com esse nome.\"}"));
    }

    @Test
    public void updateTaskSuccessfully() throws Exception {
        TaskModel taskModel = new TaskModel();
        taskModel.setId(UUID.randomUUID());
        taskModel.setName("Name");
        taskModel.setDescription("Test description");
        taskModel.setPriority("Low");

        TaskModel updateTask = new TaskModel();
        updateTask.setId(UUID.randomUUID());
        updateTask.setName("Update Name");
        updateTask.setDescription("Test description");
        updateTask.setPriority("Low");

        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(updateTask);

        when(taskService.findTaskById(any(UUID.class))).thenReturn(Optional.of(taskModel));
        when(taskService.createTask(any(TaskModel.class))).thenReturn(updateTask);

        mockMvc.perform(MockMvcRequestBuilders.put("/task/"+ UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(payload));
    }

    @Test
    public void updateTaskWithInvalidID() throws Exception {
        UUID id = UUID.randomUUID();
        TaskModel taskModel = new TaskModel();
        taskModel.setId(UUID.randomUUID());
        taskModel.setName("Test");
        taskModel.setDescription("Test description");
        taskModel.setPriority("Low");

        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(taskModel);

        when(taskService.findTaskById(id)).thenReturn(Optional.empty());


        mockMvc.perform(MockMvcRequestBuilders.put("/task/"+ id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("{\"message\":\"ID invÃ¡lido\"}"));
    }

    @Test
    public void deleteTaskSuccessfully() throws Exception{
        UUID id = UUID.randomUUID();
        TaskModel taskModel = new TaskModel();
        taskModel.setId(id);
        taskModel.setName("Test");
        taskModel.setDescription("Test description");
        taskModel.setPriority("Low");

        when(taskService.findTaskById(any(UUID.class))).thenReturn(Optional.of(taskModel));
        doNothing().when(taskService).deleteTaskById(any(UUID.class));

        mockMvc.perform(MockMvcRequestBuilders.delete("/task/" + id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("{\"message\":\"Tarefa deletada com sucesso!\"}"));
    }

    @Test
    public void deleteTaskWithInvalidID() throws Exception {
        when(taskService.findTaskById(any(UUID.class))).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.delete("/task/"+ UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("{\"message\":\"ID invÃ¡lido\"}"));
    }
}
