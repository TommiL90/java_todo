package com.timix.todo.back.exceptions;

public class UserNotFoundException  extends RuntimeException{
    public UserNotFoundException() {
        super("User not found");
    }
}
