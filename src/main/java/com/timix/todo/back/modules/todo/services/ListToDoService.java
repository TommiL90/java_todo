package com.timix.todo.back.modules.todo.services;

import com.timix.todo.back.modules.todo.entity.ToDoEntity;
import com.timix.todo.back.modules.todo.repository.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ListToDoService {
    private final ToDoRepository toDoRepository;

    @Autowired
    public ListToDoService(ToDoRepository toDoRepository) {
        this.toDoRepository = toDoRepository;
    }

    public List<ToDoEntity> execute(UUID userId) {
        return toDoRepository.findAllByUser_Id(userId);
    }
}
