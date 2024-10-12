package com.timix.todo.back.modules.todo.controller;

import com.timix.todo.back.modules.todo.Dto.CreateToDoDTO;
import com.timix.todo.back.modules.todo.services.CreateToDoService;
import com.timix.todo.back.modules.todo.services.DeleteToDoService;
import com.timix.todo.back.modules.todo.services.UpdateToDoService;
import com.timix.todo.back.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/todo")
public class DeleteToDoController {

    private final DeleteToDoService deleteToDoService;

    @Autowired
    public DeleteToDoController(DeleteToDoService deleteToDoService) {
        this.deleteToDoService = deleteToDoService;
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Object> execute(@PathVariable UUID id, HttpServletRequest request) {
        try {
            UUID userId = Utils.retrieveUserIdFromRequest(request);
            deleteToDoService.execute(id, userId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}