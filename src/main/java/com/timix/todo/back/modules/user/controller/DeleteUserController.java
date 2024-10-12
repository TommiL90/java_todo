package com.timix.todo.back.modules.user.controller;

import com.timix.todo.back.modules.user.service.DeleteUserService;
import com.timix.todo.back.modules.user.service.UpdateUserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class DeleteUserController {
    private final DeleteUserService deleteUserService;

    @Autowired
    public DeleteUserController(DeleteUserService deleteUserService) {
        this.deleteUserService = deleteUserService;
    }

    @DeleteMapping("/me")
    public ResponseEntity<Object> execute(HttpServletRequest request) {
        UUID userId = (UUID) request.getAttribute("idUser");
        try {
            deleteUserService.execute(userId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
