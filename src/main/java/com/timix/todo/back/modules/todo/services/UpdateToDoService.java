package com.timix.todo.back.modules.todo.services;

import com.timix.todo.back.exceptions.ToDoNotFoundException;
import com.timix.todo.back.modules.todo.entity.ToDoEntity;
import com.timix.todo.back.modules.todo.repository.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UpdateToDoService {

    private final ToDoRepository toDoRepository;

    @Autowired
    public UpdateToDoService(ToDoRepository toDoRepository) {
        this.toDoRepository = toDoRepository;
    }

    public ToDoEntity execute(UUID idToDo, UUID userId) throws Exception {
        var now = LocalDateTime.now();
        var toDo = toDoRepository.findById(idToDo).orElse(null);
        if (toDo == null) {
            throw new ToDoNotFoundException();
        }

        if(!toDo.getUser().getId().equals(userId)) {
            throw new Exception("Unauthorized");
        }

        if(toDo.isCompleted()) {
            toDo.setCompletedAt(null);
            toDo.setCompleted(false);
        } else {
            toDo.setCompletedAt(now);
            toDo.setCompleted(true);
        }
        return toDoRepository.save(toDo);
    }
}