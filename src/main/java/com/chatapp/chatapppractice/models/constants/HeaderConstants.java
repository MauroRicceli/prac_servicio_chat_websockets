package com.chatapp.chatapppractice.models.constants;

public final class HeaderConstants {

    private HeaderConstants() { };

    /**
     * Prefix used in the tokens.
     */
    public static final String BEARER_PREFIX = "Bearer ";
    /**
     * Length of the bearer inside the header used to remove it with substring.
     */
    public static final int BEARER_LENGTH_HEADER = 7;
}

