package com.chatapp.chatapppractice.security.exceptions;

public class UserAlreadyRegisteredException extends RuntimeException {

    public UserAlreadyRegisteredException(final String message) {
        super(message);
    }
}
