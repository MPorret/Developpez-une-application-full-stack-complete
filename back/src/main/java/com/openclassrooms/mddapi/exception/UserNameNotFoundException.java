package com.openclassrooms.mddapi.exception;

public class UserNameNotFoundException extends RuntimeException {
    public UserNameNotFoundException(String name) {
        super("User not found with name : " + name);
    }
}
