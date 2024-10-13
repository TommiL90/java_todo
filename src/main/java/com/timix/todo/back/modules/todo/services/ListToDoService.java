package com.timix.todo.back.modules.todo.services;

import com.timix.todo.back.modules.todo.entity.ToDoEntity;
import com.timix.todo.back.modules.todo.repository.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ListToDoService {
    private final ToDoRepository toDoRepository;

    @Autowired
    public ListToDoService(ToDoRepository toDoRepository) {
        this.toDoRepository = toDoRepository;
    }

    public List<ToDoEntity> execute(UUID userId, Optional<String> q) {
        List<ToDoEntity> todoList;

        if (q.isPresent()) {
            todoList = toDoRepository.findByUserIdAndTitleContainingIgnoreCase(userId, q.get());
        } else {
            todoList = toDoRepository.findByUserId(userId);
        }

        return todoList
                .stream()
                .sorted((t1, t2) -> {
                    if (t1.isCompleted() == t2.isCompleted()) {
                        return t2.getCreatedAt().compareTo(t1.getCreatedAt());
                    }
                    return Boolean.compare(t1.isCompleted(), t2.isCompleted());
        }).collect(Collectors.toList());
    }
}
