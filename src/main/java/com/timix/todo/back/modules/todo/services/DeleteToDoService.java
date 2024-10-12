package com.timix.todo.back.modules.todo.services;

import com.timix.todo.back.exceptions.ToDoNotFoundException;
import com.timix.todo.back.modules.todo.repository.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeleteToDoService {

    private final ToDoRepository toDoRepository;

    @Autowired
    public DeleteToDoService(ToDoRepository toDoRepository) {
        this.toDoRepository = toDoRepository;
    }

    public void execute(UUID idToDo, UUID userId) throws Exception {
        var toDo = toDoRepository.findById(idToDo).orElse(null);
        if (toDo == null) {
            throw new ToDoNotFoundException();
        }

        if(!toDo.getUser().getId().equals(userId)) {
            throw new Exception("Unauthorized");
        }

        toDoRepository.deleteById(idToDo);
    }
}
