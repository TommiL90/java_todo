package com.timix.todo.back.modules.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


import java.util.UUID;

@Data
public class AuthDTO {

    @NotBlank(message = "Username is required")
    private String username;


    @NotBlank(message = "Password is required")
    private String password;
}
