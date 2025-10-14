package com.chatapp.chatapppractice.security.exceptions;

public class PostDoesntExistsException extends RuntimeException {
    public PostDoesntExistsException(String message) {
        super(message);
    }
}
