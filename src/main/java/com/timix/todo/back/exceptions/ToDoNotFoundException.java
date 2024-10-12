package com.timix.todo.back.exceptions;


public class ToDoNotFoundException  extends RuntimeException{
    public ToDoNotFoundException() {
        super("ToDo not found");
    }
}
