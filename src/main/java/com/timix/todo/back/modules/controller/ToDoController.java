package com.timix.todo.back.modules.controller;

import com.timix.todo.back.modules.todo.entity.ToDoEntity;
import com.timix.todo.back.modules.todo.repository.ToDoRepository;
import com.timix.todo.back.modules.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/todo")
public class ToDoController {

    @Autowired
    private ToDoRepository toDoRepository;

    @Autowired
    private UserRepository userRepository;


    @PostMapping()
    public ResponseEntity<?> create(@RequestBody ToDoEntity toDoEntity, HttpServletRequest request) {
        UUID userId = (UUID) request.getAttribute("idUser");
        var user = userRepository.findById(userId).orElse(null);
        if( user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
        }


        toDoEntity.setUser(user);
        var newToDo = toDoRepository.save(toDoEntity);

        return ResponseEntity.status(HttpStatus.CREATED).body(newToDo);

    }


    @GetMapping()
    public ResponseEntity<List<ToDoEntity>> getToDos( HttpServletRequest request) {
        UUID userId = (UUID) request.getAttribute("idUser");
        var user = userRepository.findById(userId).orElse(null);

        return ResponseEntity.status(HttpStatus.OK).body(toDoRepository.findAllByUser_Id(userId));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> complete(@PathVariable UUID id,  HttpServletRequest request) {
        UUID userId = (UUID) request.getAttribute("idUser");
        var now = LocalDateTime.now();
        boolean completed = true;

        var todo = toDoRepository.findById(id).orElse(null);

        if(todo == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ToDo not found");
        }

        if(!todo.getUser().getId().equals(userId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }

        if(todo.isCompleted()) {
            todo.setCompletedAt(null);
            todo.setCompleted(false);
        } else {
            todo.setCompletedAt(now);
            todo.setCompleted(completed);
        }


        var updatedToDo = toDoRepository.save(todo);

        return ResponseEntity.status(HttpStatus.OK).body(updatedToDo);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable UUID id,  HttpServletRequest request) {
        UUID userId = (UUID) request.getAttribute("idUser");
        try {
            toDoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
