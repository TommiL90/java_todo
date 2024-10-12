package com.timix.todo.back.modules.todo.services;

import com.timix.todo.back.modules.todo.Dto.CreateToDoDTO;
import com.timix.todo.back.modules.todo.entity.ToDoEntity;
import com.timix.todo.back.modules.todo.repository.ToDoRepository;
import com.timix.todo.back.modules.user.entity.UserEntity;

import com.timix.todo.back.modules.user.service.GetByIdUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CreateToDoService {

    private final ToDoRepository toDoRepository;
    private final GetByIdUserService getByIdUserService;
    private final ModelMapper modelMapper;

    @Autowired
    public CreateToDoService(ToDoRepository toDoRepository, GetByIdUserService getByIdUserService, ModelMapper modelMapper) {
        this.toDoRepository = toDoRepository;
        this.getByIdUserService = getByIdUserService;
        this.modelMapper = modelMapper;
    }

    public ToDoEntity execute(CreateToDoDTO createToDoDTO, UUID id) {
        var user = getByIdUserService.execute(id);
        var toDoEntity = modelMapper.map(createToDoDTO, ToDoEntity.class);
        var userEntity = modelMapper.map(user, UserEntity.class);
        toDoEntity.setUser(userEntity);
        return toDoRepository.save(toDoEntity);
    }
}