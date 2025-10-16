package com.chatapp.chatapppractice.security.handlers;

import com.chatapp.chatapppractice.security.exceptions.CommentDoesntExistsException;
import com.chatapp.chatapppractice.security.exceptions.PostDoesntExistsException;
import com.chatapp.chatapppractice.security.exceptions.UnauthorizedActionOnPostException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class PostHandler {

    /**
     * This exception gets threw when the user tries to get a non-existent post.
     * @param e Message of the exception.
     * @return Response entity with the message and the HTTP Code.
     */
    @ExceptionHandler(exception = PostDoesntExistsException.class)
    public ResponseEntity<String> handlerPostDoesntExistsException(final PostDoesntExistsException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * This exception gets threw when the user tries to perform an not authorized action on a post out of his property.
     * @param e Message of the exception.
     * @return Response entity with the message and the HTTP Code.
     */
    @ExceptionHandler(exception = UnauthorizedActionOnPostException.class)
    public ResponseEntity<String> handlerUnauthorizedActionOnPostException(final UnauthorizedActionOnPostException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
    }

    /**
     * This exception gets threw when the user tries to get a non-existent comment in a post.
     * @param e Message of the exception.
     * @return Response entity with the message and the HTTP Code.
     */
    @ExceptionHandler(exception = CommentDoesntExistsException.class)
    public ResponseEntity<String> handlerCommentDoesntExistsException(final CommentDoesntExistsException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
