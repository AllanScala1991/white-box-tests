package com.example.tasks.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskDTO {

    @NotBlank
    String name;

    @NotBlank
    String description;

    @NotBlank
    String priority;

}
