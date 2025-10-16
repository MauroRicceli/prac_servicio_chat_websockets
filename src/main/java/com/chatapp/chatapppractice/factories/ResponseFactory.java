package com.chatapp.chatapppractice.factories;

import com.chatapp.chatapppractice.models.dtos.authdtos.AuthResponseDTO;
import com.chatapp.chatapppractice.models.dtos.userinteractiondtos.FriendshipResponseDTO;
import com.chatapp.chatapppractice.models.entities.FriendshipEntity;
import com.chatapp.chatapppractice.models.entities.UserEntity;


public final class ResponseFactory {

    private ResponseFactory() { };

    /**
     * This method creates a new AuthResponseDTO with all the given info.
     * @param user that interacted with the endpoint.
     * @param refreshToken generated for the useractions.
     * @param accessToken generated for the useractions.
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

    /**
     * This method creates a new FriendshipResponseDTO with all the given info.
     * @param user Entity with all the useractions data.
     * @param friendships Entity with all the friendships data of that useractions.
     * @return FriendshipResponseDTO with all the needed info.
     */
    public static FriendshipResponseDTO createFriendshipResponse(final UserEntity user, final FriendshipEntity friendships) {
        return FriendshipResponseDTO.builder()
                .id(user.getId().toString())
                .email(user.getEmail())
                .friends(friendships.getFriendList())
                .build();
    }
}
