package com.chatapp.chatapppractice.security.exceptions;

public class CommentDoesntExistsException extends RuntimeException {
    public CommentDoesntExistsException(final String message) {
        super(message);
    }
}
