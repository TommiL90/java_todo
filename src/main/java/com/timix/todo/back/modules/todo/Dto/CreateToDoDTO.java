package com.timix.todo.back.modules.todo.Dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;


@Data
public class CreateToDoDTO {

    @NotBlank(message = "Title is required")
    @Length(min = 5, max = 15, message = "Title must be between 5 and 50 characters")
    private String title;

    @NotBlank(message = "Description is required")
    @Length(min = 5, max = 250, message = "Description must be between 5 and 250 characters")
    private String description;

}
