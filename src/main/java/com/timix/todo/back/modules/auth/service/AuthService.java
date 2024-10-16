package com.timix.todo.back.modules.auth.service;

import com.timix.todo.back.exceptions.UserNotFoundException;
import com.timix.todo.back.modules.auth.dto.AuthDTO;
import com.timix.todo.back.modules.user.service.GetUserByUsername;
import com.timix.todo.back.security.JWTProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final GetUserByUsername getUserByUsername;
    private final JWTProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(GetUserByUsername getUserByUsername, JWTProvider jwtProvider, PasswordEncoder passwordEncoder) {
        this.getUserByUsername = getUserByUsername;
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = passwordEncoder;
    }

    public String login(AuthDTO authDTO) {

        var user = getUserByUsername.execute(authDTO.getUsername());
        System.out.println("AuthService.login" + user);
        if (user == null) {
            throw new UserNotFoundException();
        }
        System.out.println("AuthService.login" + 2);
        if (!validatePassword(authDTO.getPassword(), user.getPassword())) {
            throw new UserNotFoundException();
        }
        System.out.println("AuthService.login" + 3);
        return jwtProvider.generateToken(user.getUsername(), user.getId());
    }

    private boolean validatePassword(String password, String hashedPassword) {
        return passwordEncoder.matches(password, hashedPassword);
    }
}
