package com.chatapp.chatapppractice.factories;

import com.chatapp.chatapppractice.models.entities.TokenEntity;
import com.chatapp.chatapppractice.models.entities.UserEntity;

public final class TokenFactory {

    private TokenFactory() { };

    /**
     * This method creates and return a token entity for the user with the given information.
     * @param refreshToken refresh token generated for the user.
     * @param userOwner user owner of the token.
     * @return TokenEntity with all the needed info.
     */
    public static TokenEntity createTokenEntity(final String refreshToken, final UserEntity userOwner) {

        return TokenEntity.builder()
                .token(refreshToken)
                .revoked(false)
                .expired(false)
                .userOwner(userOwner)
                .build();

    }
}
