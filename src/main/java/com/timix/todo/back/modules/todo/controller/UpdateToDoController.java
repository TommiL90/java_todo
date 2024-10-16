package com.timix.todo.back.modules.todo.controller;
import com.timix.todo.back.modules.todo.services.UpdateToDoService;
import com.timix.todo.back.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/todo")
public class UpdateToDoController {

    private final UpdateToDoService updateToDoService;

    @Autowired
    public UpdateToDoController(UpdateToDoService updateToDoService) {
        this.updateToDoService = updateToDoService;
    }


    @PatchMapping("/{id}")
    public ResponseEntity<Object> execute(@PathVariable UUID id, HttpServletRequest request) {
        try {
            UUID userId = Utils.retrieveUserIdFromRequest(request);
            var result = updateToDoService.execute(id, userId);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}