package com.timix.todo.back;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @RequestMapping("/")
    public String health() {
        System.out.println("Health check passed");
        return "OK";
    }
}
