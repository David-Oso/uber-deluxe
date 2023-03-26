package com.example.uberDeluxe.exception;

public class UserNotFoundException extends BusinessLogicException{
    public UserNotFoundException(String message) {
        super(message);
    }
}
