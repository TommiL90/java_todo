package com.timix.todo.back.modules.user.service;

import com.timix.todo.back.exceptions.UserNotFoundException;
import com.timix.todo.back.modules.user.entity.UserEntity;
import com.timix.todo.back.modules.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetUserByUsername {
    private final UserRepository userRepository;

    @Autowired
    public GetUserByUsername(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity execute(String username) {
        var user = this.userRepository.findByUsername(username).orElse(null);

        if (user == null) {
            throw new UserNotFoundException();
        }

        return user;
    }

}
