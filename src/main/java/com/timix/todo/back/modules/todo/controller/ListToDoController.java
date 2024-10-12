package com.timix.todo.back.modules.todo.controller;

import com.timix.todo.back.modules.todo.entity.ToDoEntity;
import com.timix.todo.back.modules.todo.services.ListToDoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/todo")
public class ListToDoController {
    private final ListToDoService listToDoService;

    @Autowired
    public ListToDoController(ListToDoService listToDoService) {
        this.listToDoService = listToDoService;
    }

    @GetMapping()
    public ResponseEntity<List<ToDoEntity>> execute(HttpServletRequest request) {
        try {
            UUID userId = (UUID) request.getAttribute("idUser");
            var result = listToDoService.execute(userId);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
