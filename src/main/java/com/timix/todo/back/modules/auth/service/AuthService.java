package com.timix.todo.back.modules.auth.service;

import com.timix.todo.back.exceptions.UserNotFoundException;
import com.timix.todo.back.modules.auth.dto.AuthDTO;
import com.timix.todo.back.modules.user.service.UserService;
import com.timix.todo.back.security.JWTProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserService userService;
    private final JWTProvider jwtProvider;

    @Autowired
    public AuthService(UserService userService, JWTProvider jwtProvider) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
    }

    public String login(AuthDTO authDTO) {

        var user = userService.getUserByUsername(authDTO.getUsername());

        if (user == null) {
            throw new UserNotFoundException();
        }

        if (!validatePassword(authDTO.getPassword(), user.getPassword())) {
            throw new UserNotFoundException();
        }

        return jwtProvider.generateToken(user.getUsername(), user.getId());
    }

    private boolean validatePassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}
