package com.chatapp.chatapppractice.models.constants;

public final class ErrorMessages {

    private ErrorMessages() { };

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
}
