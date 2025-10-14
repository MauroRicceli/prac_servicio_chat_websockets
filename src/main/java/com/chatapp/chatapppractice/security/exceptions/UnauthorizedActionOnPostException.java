package com.chatapp.chatapppractice.security.exceptions;

public class UnauthorizedActionOnPostException extends RuntimeException {
    public UnauthorizedActionOnPostException(final String message) {
        super(message);
    }
}
