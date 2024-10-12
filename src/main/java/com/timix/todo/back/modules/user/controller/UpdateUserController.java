package com.timix.todo.back.modules.user.controller;

import com.timix.todo.back.modules.user.dto.UserUpdateDTO;
import com.timix.todo.back.modules.user.service.UpdateUserService;
import com.timix.todo.back.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UpdateUserController {
    private final UpdateUserService updateUserService;

    public UpdateUserController(UpdateUserService updateUserService) {
        this.updateUserService = updateUserService;
    }

    @PutMapping("/me")
    public ResponseEntity<Object> execute(@RequestBody UserUpdateDTO userUpdateDTO, HttpServletRequest request) {
        try {
            UUID userId = Utils.retrieveUserIdFromRequest(request);
            var result = updateUserService.execute(userUpdateDTO, userId);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
