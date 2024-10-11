package com.timix.todo.back.modules.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.timix.todo.back.modules.todo.entity.ToDoEntity;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Data
@Entity(name = "tb_users")
@JsonIgnoreProperties(value = "todos")
public class UserEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(unique = true)
    private String username;
    private String name;
    private String password;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<ToDoEntity> todos;

}