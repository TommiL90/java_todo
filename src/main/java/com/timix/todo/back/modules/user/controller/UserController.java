package com.timix.todo.back.modules.user.controller;


import com.timix.todo.back.modules.user.dto.UserCreateDTO;
import com.timix.todo.back.modules.user.entity.UserEntity;
import com.timix.todo.back.modules.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<Object> create(@Valid @RequestBody UserCreateDTO userCreateDTO) {
        try {
            var result = userService.createUser(userCreateDTO);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
