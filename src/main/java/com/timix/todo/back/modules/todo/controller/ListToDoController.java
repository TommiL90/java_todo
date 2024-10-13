package com.timix.todo.back.modules.todo.controller;

import com.timix.todo.back.modules.todo.entity.ToDoEntity;
import com.timix.todo.back.modules.todo.services.ListToDoService;
import com.timix.todo.back.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
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
    public ResponseEntity<List<ToDoEntity>> execute(HttpServletRequest request, @RequestParam(required = false) String q) {
        try {
            UUID userId = Utils.retrieveUserIdFromRequest(request);
            var result = listToDoService.execute(userId, Optional.ofNullable(q));
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
