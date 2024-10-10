
package com.timix.todo.back.modules.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserUpdateDTO extends UserCreateDTO {


    @Length(min = 5, max = 15, message = "Username must be between 3 and 20 characters")
    private String username;

    @Length(min = 5, max = 15, message = "Username must be between 3 and 20 characters")
    private String name;

    @Length(min = 5, max = 15, message = "Password must be between 5 and 15 characters")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{5,15}$", message = "Password must contain at least one digit, one lowercase letter, one uppercase letter, and no whitespace")
    private String password;
}