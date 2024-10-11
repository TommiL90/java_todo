package com.timix.todo.back.modules.user.service;

import com.timix.todo.back.exceptions.UserFoundException;
import com.timix.todo.back.exceptions.UserNotFoundException;
import com.timix.todo.back.modules.user.dto.UserCreateDTO;
import com.timix.todo.back.modules.user.dto.UserResponseDTO;
import com.timix.todo.back.modules.user.dto.UserUpdateDTO;
import com.timix.todo.back.modules.user.entity.UserEntity;
import com.timix.todo.back.modules.user.repository.UserRepository;
import com.timix.todo.back.utils.Utils;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserResponseDTO createUser(@Valid UserCreateDTO userCreateDTO) {
        this.userRepository.findByUsername(userCreateDTO.getUsername()).ifPresent(user -> {
            throw new UserFoundException();
        });

        var userEntity = modelMapper.map(userCreateDTO, UserEntity.class);

        var hashedPassword = BCrypt.hashpw(userCreateDTO.getPassword(), BCrypt.gensalt());

        userEntity.setPassword(hashedPassword);

        var savedUser = userRepository.save(userEntity);

        return modelMapper.map(savedUser, UserResponseDTO.class);
    }

    public UserResponseDTO getUserById(UUID id) {
        var user = userRepository.findById(id).orElse(null);

        if (user == null) {
            throw new UserNotFoundException();
        }

        return modelMapper.map(user, UserResponseDTO.class);
    }

    public UserEntity getUserByUsername(String username) {
        return this.userRepository.findByUsername(username).orElseThrow(UserFoundException::new);
    }


    public UserResponseDTO updateUser(UserUpdateDTO userUpdateDTO, UUID id) {
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

    public void deleteUser(UUID id) {
        var user = userRepository.findById(id).orElse(null);

        if (user == null) {
            throw new UserNotFoundException();
        }

        userRepository.deleteById(id);
    }


}