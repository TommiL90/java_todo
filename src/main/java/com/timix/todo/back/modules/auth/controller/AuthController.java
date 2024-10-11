package com.timix.todo.back.modules.auth.controller;


import com.timix.todo.back.modules.auth.dto.AuthDTO;
import com.timix.todo.back.modules.auth.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthController {
    @Autowired
    private AuthService authService;


    @PostMapping()
    public ResponseEntity<String> login(@Valid @RequestBody AuthDTO authDTO) {
        try {
            var result = authService.login(authDTO);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
