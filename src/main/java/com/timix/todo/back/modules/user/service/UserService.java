package com.timix.todo.back.modules.user.service;

import com.timix.todo.back.exceptions.UserFoundException;
import com.timix.todo.back.exceptions.UserNotFoundException;
import com.timix.todo.back.modules.user.dto.UserCreateDTO;
import com.timix.todo.back.modules.user.entity.UserEntity;
import com.timix.todo.back.modules.user.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserEntity createUser(@Valid UserCreateDTO userEntity) {
        this.userRepository.findByUsername(userEntity.getUsername()).ifPresent(user -> {
            throw new UserFoundException();
        });
        return userRepository.save(userEntity);
    }

    public UserEntity getUserById(UUID id) {
        var user = userRepository.findById(id).orElse(null);

        if (user == null) {
            throw new UserNotFoundException();
        }

        return user;
    }


    public UserEntity updateUser(UserEntity userEntity) {
        var user = userRepository.findById(userEntity.getId()).orElse(null);

        if (user == null) {
            throw new UserNotFoundException();
        }

        this.userRepository.findByUsername(userEntity.getUsername()).ifPresent(u -> {
            if (!u.getId().equals(userEntity.getId())) {
                throw new UserFoundException();
            }
        });

        return userRepository.save(userEntity);
    }

    public void deleteUser(UUID id) {
        var user = userRepository.findById(id).orElse(null);

        if (user == null) {
            throw new UserNotFoundException();
        }

        userRepository.deleteById(id);
    }
}