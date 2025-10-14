package com.chatapp.chatapppractice.security.handlers;

import com.chatapp.chatapppractice.security.exceptions.PostDoesntExistsException;
import com.chatapp.chatapppractice.security.exceptions.UnauthorizedActionOnPostException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class PostHandler {

    /**
     * This exception gets threw when the user tries to get a non-existent post.
     * @param e Message of the exception.
     * @return Response entity with the message and the HTTP Code.
     */
    public ResponseEntity<String> handlerPostDoesntExistsException(final PostDoesntExistsException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * This exception gets threw when the user tries to perform an not authorized action on a post out of his property.
     * @param e Message of the exception.
     * @return Response entity with the message and the HTTP Code.
     */
    public ResponseEntity<String> handlerUnauthorizedActionOnPostException(final UnauthorizedActionOnPostException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
    }
}
