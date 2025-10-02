package com.chatapp.chatapppractice.models.factory;

import com.chatapp.chatapppractice.models.dtos.RegisterRequestDTO;
import com.chatapp.chatapppractice.models.entities.UserEntity;


public final class UserFactory {

    private UserFactory() { };

    /**
     * Creates the user entity with the registerDTO data, hashes the password.
     * @param registerRequestDTO DTO with the user data.
     * @param hashedPw hashed password created from the one sent by the user.
     * @return UserEntity created with that data.
     */
    public static UserEntity registerDTOToUserEntity(final RegisterRequestDTO registerRequestDTO, final String hashedPw) {

        return UserEntity.builder()
                .firstname(registerRequestDTO.getFirstname())
                .lastname(registerRequestDTO.getLastname())
                .fullname(registerRequestDTO.getFirstname() + " " + registerRequestDTO.getLastname())
                .userRole(registerRequestDTO.getUserRole())
                .email(registerRequestDTO.getEmail())
                .password(hashedPw)
                .username(registerRequestDTO.getUsername())
                .build();

    }
}
