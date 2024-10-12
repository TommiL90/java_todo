package com.timix.todo.back.modules.user.service;

import com.timix.todo.back.exceptions.UserNotFoundException;
import com.timix.todo.back.modules.user.dto.UserResponseDTO;
import com.timix.todo.back.modules.user.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetByIdUserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public GetByIdUserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public UserResponseDTO execute(UUID id) {
        var user = userRepository.findById(id).orElse(null);

        if (user == null) {
            throw new UserNotFoundException();
        }

        return modelMapper.map(user, UserResponseDTO.class);
    }
}
