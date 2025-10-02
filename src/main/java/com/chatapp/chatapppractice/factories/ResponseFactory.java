package com.chatapp.chatapppractice.factories;

import com.chatapp.chatapppractice.models.dtos.AuthResponseDTO;
import com.chatapp.chatapppractice.models.entities.UserEntity;


public final class ResponseFactory {

    private ResponseFactory() { };

    /**
     * This method created a new AuthResponseDTO with all the given info.
     * @param user that interacted with the endpoint.
     * @param refreshToken generated for the user.
     * @param accessToken generated for the user.
     * @return AuthResponseDTO with all the needed info.
     */
    public static AuthResponseDTO createAuthResponse(final UserEntity user, final String refreshToken, final String accessToken) {
        return AuthResponseDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .userRole(user.getUserRole())
                .username(user.getUsername())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
