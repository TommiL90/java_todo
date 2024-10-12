package com.timix.todo.back.modules.user.service;

import com.timix.todo.back.exceptions.UserFoundException;
import com.timix.todo.back.modules.user.dto.UserCreateDTO;
import com.timix.todo.back.modules.user.dto.UserResponseDTO;
import com.timix.todo.back.modules.user.entity.UserEntity;
import com.timix.todo.back.modules.user.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateUserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public CreateUserService(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponseDTO execute(UserCreateDTO userCreateDTO) {
        this.userRepository.findByUsername(userCreateDTO.getUsername()).ifPresent(user -> {
            throw new UserFoundException();
        });

        var userEntity = modelMapper.map(userCreateDTO, UserEntity.class);

        // Encode password
        userEntity.setPassword(passwordEncoder.encode(userCreateDTO.getPassword()));

        var savedUser = userRepository.save(userEntity);

        return modelMapper.map(savedUser, UserResponseDTO.class);
    }
}
