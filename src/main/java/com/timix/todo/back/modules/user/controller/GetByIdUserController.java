package com.timix.todo.back.modules.user.controller;

import com.timix.todo.back.modules.user.service.GetByIdUserService;
import com.timix.todo.back.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class GetByIdUserController {
    private final GetByIdUserService getByIdUserService;

    @Autowired
    public GetByIdUserController(GetByIdUserService getByIdUserService) {
        this.getByIdUserService = getByIdUserService;
    }

    @GetMapping("/me")
    public ResponseEntity<Object> execute(HttpServletRequest request) {
        try {
            UUID userId = Utils.retrieveUserIdFromRequest(request);
            var result = getByIdUserService.execute(userId);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
