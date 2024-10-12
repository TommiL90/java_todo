package com.timix.todo.back.modules.user.service;


import com.timix.todo.back.exceptions.UserFoundException;
import com.timix.todo.back.exceptions.UserNotFoundException;
import com.timix.todo.back.modules.user.dto.UserResponseDTO;
import com.timix.todo.back.modules.user.dto.UserUpdateDTO;
import com.timix.todo.back.modules.user.repository.UserRepository;
import com.timix.todo.back.utils.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UpdateUserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UpdateUserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public UserResponseDTO execute(UserUpdateDTO userUpdateDTO, UUID id) {
        var user = userRepository.findById(id).orElse(null);

        if (user == null) {
            throw new UserNotFoundException();
        }

        this.userRepository.findByUsername(userUpdateDTO.getUsername()).ifPresent(u -> {
            if (!u.getId().equals(id)){
                throw new UserFoundException();
            }
        });

        Utils.copyNonNullProperties(userUpdateDTO, user);

        var updatedUser = userRepository.save(user);

        return modelMapper.map(updatedUser, UserResponseDTO.class);
    }
}
