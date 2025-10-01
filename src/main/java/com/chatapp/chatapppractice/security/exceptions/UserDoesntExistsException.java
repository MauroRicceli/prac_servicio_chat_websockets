package com.chatapp.chatapppractice.security.exceptions;

public class UserDoesntExistsException extends RuntimeException {

    public UserDoesntExistsException(final String message) {
        super(message);
    }
}
