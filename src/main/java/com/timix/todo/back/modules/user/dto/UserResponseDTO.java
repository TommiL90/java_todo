package com.timix.todo.back.modules.user.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class UserResponseDTO {
    private UUID id;
    private String username;
    private String name;
    private LocalDateTime createdAt;

    @JsonIgnore
    private String password;
}
