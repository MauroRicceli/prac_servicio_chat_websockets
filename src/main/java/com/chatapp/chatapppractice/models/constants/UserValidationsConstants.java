package com.chatapp.chatapppractice.models.constants;

public final class UserValidationsConstants {

    private UserValidationsConstants() { };

    /**
     * Minimum size of the string for the real names of the useractions. Firstname, Lastname, Fullname.
     */
    public static final int MIN_SIZE_REAL_NAMES_USER = 2;
    /**
     * Minimum size of the string for the username of the useractions.
     */
    public static final int MIN_SIZE_USERNAME = 3;
    /**
     * Maximum size of the string for the real names of the useractions. Firstname, Lastname, Fullname.
     */
    public static final int MAX_SIZE_REAL_NAMES_USER = 70;
    /**
     * Maximum size of the string for the username of the useractions.
     */
    public static final int MAX_SIZE_USERNAME = 60;
}
