package com.timix.todo.back.modules.todo.repository;

import com.timix.todo.back.modules.todo.entity.ToDoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ToDoRepository extends JpaRepository<ToDoEntity, UUID> {

    List<ToDoEntity> findByUserIdAndTitleContainingIgnoreCase(UUID userId, String title);

    List<ToDoEntity> findByUserId(UUID userId);
}
