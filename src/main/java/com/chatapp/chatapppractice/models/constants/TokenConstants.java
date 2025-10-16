package com.chatapp.chatapppractice.models.constants;

public final class TokenConstants {

    private TokenConstants() { };

    /**
     * Role of the useractions saved inside the token.
     */
    public static final String CLAIMS_USER_ROLE = "role";
    /**
     * Type of the token. Access or refresh.
     */
    public static final String CLAIMS_TOKEN_TYPE = "type";
    /**
     * Username of the useractions inside the application. Not the token username.
     */
    public static final String CLAIMS_USER_APP_USERNAME = "app_username";
    /**
     * Prefix used for refresh type tokens.
     */
    public static final String TOKEN_TYPE_REFRESH_PREFIX = "refresh";
    /**
     * Prefix used for access type tokens.
     */
    public static final String TOKEN_TYPE_ACCESS_PREFIX = "access";

}

