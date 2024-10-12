package com.timix.todo.back.modules.user.service;

import com.timix.todo.back.exceptions.UserNotFoundException;
import com.timix.todo.back.modules.user.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeleteUserService {
    private final UserRepository userRepository;


    @Autowired
    public DeleteUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute(UUID id) {
        var user = userRepository.findById(id).orElse(null);

        if (user == null) {
            throw new UserNotFoundException();
        }

        userRepository.deleteById(id);
    }
}
