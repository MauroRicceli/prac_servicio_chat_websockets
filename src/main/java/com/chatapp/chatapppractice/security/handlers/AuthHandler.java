package com.chatapp.chatapppractice.security.handlers;

import com.chatapp.chatapppractice.security.exceptions.LoginCredentialsDoesntMatchesException;
import com.chatapp.chatapppractice.security.exceptions.UserAlreadyRegisteredException;
import com.chatapp.chatapppractice.security.exceptions.UserDoesntExistsException;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AuthHandler {

    /**
     * This exception gets threw when a user doesn't exist in the database.
     * @param e Message of the exception
     * @return Response entity with the message and the HTTP Code
     */
    @ExceptionHandler(exception = UserDoesntExistsException.class)
    public ResponseEntity<String> handlerUserDoesntExistsException(final UserDoesntExistsException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * This exception gets threw when a user exist in the database and its trying to register again.
     * @param e Message of the exception
     * @return Response entity with the message and the HTTP Code
     */
    @ExceptionHandler(exception = UserAlreadyRegisteredException.class)
    public ResponseEntity<String> handlerUserAlreadyRegisteredException(final UserAlreadyRegisteredException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * This exception gets threw when a user tries to log in with an incorrect credential.
     * @param e Message of the exception
     * @return Response entity with the message and the HTTP Code
     */
    @ExceptionHandler(exception = LoginCredentialsDoesntMatchesException.class)
    public ResponseEntity<String> hanlderLoginCredentialsDoesntMatchesException(final LoginCredentialsDoesntMatchesException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }
}
