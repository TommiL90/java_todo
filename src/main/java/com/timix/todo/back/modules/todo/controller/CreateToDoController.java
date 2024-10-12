package com.timix.todo.back.modules.todo.controller;

import com.timix.todo.back.modules.todo.Dto.CreateToDoDTO;
import com.timix.todo.back.modules.todo.services.CreateToDoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/todo")
public class CreateToDoController {

    private final CreateToDoService createToDoService;

    @Autowired
    public CreateToDoController(CreateToDoService createToDoService) {
        this.createToDoService = createToDoService;
    }


    @PostMapping()
    public ResponseEntity<?> create(@Valid @RequestBody CreateToDoDTO createToDoDTO, HttpServletRequest request) {
        try {
            UUID userId = (UUID) request.getAttribute("idUser");
            var result = createToDoService.execute(createToDoDTO, userId);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
