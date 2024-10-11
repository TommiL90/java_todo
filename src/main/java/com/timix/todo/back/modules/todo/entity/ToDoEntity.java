package com.timix.todo.back.modules.todo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.timix.todo.back.modules.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "tb_todos")
@JsonIgnoreProperties(value = "user")
public class ToDoEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    private String title;

    private String description;

    @Column(columnDefinition = "boolean default false")
    private boolean completed;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(nullable = true)
    private LocalDateTime completedAt;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;
}
