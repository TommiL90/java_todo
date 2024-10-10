package com.timix.todo.back.exceptions;

public class UserFoundException  extends RuntimeException{
    public UserFoundException() {
        super("User already exists");
    }
}
