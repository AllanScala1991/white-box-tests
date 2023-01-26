package com.example.tasks.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "Tasks")
public class TaskModel {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    //name
    @Column(nullable = false)
    private String name;

    //description
    @Column(nullable = false)
    private String description;

    //priority
    @Column(nullable = false)
    private String priority;
}
