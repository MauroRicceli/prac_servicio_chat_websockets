package com.chatapp.chatapppractice.security.exceptions;

public class LoginCredentialsDoesntMatchesException extends RuntimeException {

    public LoginCredentialsDoesntMatchesException(final String message) {
        super(message);
    }
}
