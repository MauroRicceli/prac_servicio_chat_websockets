package com.chatapp.chatapppractice.models.constants;

public final class ErrorMessagesConstants {

    private ErrorMessagesConstants() { };

    /**
     * Message for the login credentials error.
     */
    public static final String PASSWORD_DOESNT_MATCH = "The password given doesn't matches";
    /**
     * Message for the user already registered error.
     */
    public static final String USER_ALREADY_REGISTERED = "The email is already registered";
    /**
     * Message for the user doesn't exist error.
     */
    public static final String USER_DOESNT_EXISTS = "The email isn't registered";
    /**
     * Message for the post doesn't exist error.
     */
    public static final String POST_DOESNT_EXISTS = "The post doesn't exists";
    /**
     * Message for the unauthorized on post action error.
     */
    public static final String UNAUTHORIZED_ON_POST_ACTION = "The active user isn't authorized to perform that action on the post";
}
