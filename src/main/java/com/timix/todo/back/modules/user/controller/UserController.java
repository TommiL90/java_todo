package com.timix.todo.back.modules.user.controller;


import com.timix.todo.back.modules.user.dto.UserCreateDTO;
import com.timix.todo.back.modules.user.dto.UserUpdateDTO;
import com.timix.todo.back.modules.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Object> create(@Valid @RequestBody UserCreateDTO userCreateDTO) {
        try {
            var result = userService.createUser(userCreateDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/me")
    public ResponseEntity<Object> getCurrentUser(HttpServletRequest request) {
        UUID userId = (UUID) request.getAttribute("idUser");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User ID not found in request");
        }
        try {
            var result = userService.getUserById(userId);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PutMapping("/me")
    public ResponseEntity<Object> updateUser(@RequestBody UserUpdateDTO userUpdateDTO, HttpServletRequest request) {
        UUID userId = (UUID) request.getAttribute("idUser");
        try {
            var result = userService.updateUser(userUpdateDTO, userId);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/me")
    public ResponseEntity<Object> deleteUser(HttpServletRequest request) {
        UUID userId = (UUID) request.getAttribute("idUser");
        try {
            userService.deleteUser(userId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
