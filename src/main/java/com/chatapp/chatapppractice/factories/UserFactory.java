package com.chatapp.chatapppractice.factories;

import com.chatapp.chatapppractice.configs.EncryptConfig;
import com.chatapp.chatapppractice.models.dtos.RegisterRequestDTO;
import com.chatapp.chatapppractice.models.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserFactory {

    /**
     * Used to encrypt the passwords
     */
    private final EncryptConfig encryptConfig;

    /**
     * Creates the user entity with the registerDTO data, hashes the password.
     * @param registerRequestDTO DTO with the user data.
     * @return UserEntity created with that data.
     */
    public UserEntity registerDTOToUserEntity(final RegisterRequestDTO registerRequestDTO) {

        return UserEntity.builder()
                .firstname(registerRequestDTO.getFirstname())
                .lastname(registerRequestDTO.getLastname())
                .fullname(registerRequestDTO.getFirstname() + " " + registerRequestDTO.getLastname())
                .userRole(registerRequestDTO.getUserRole())
                .email(registerRequestDTO.getEmail())
                .password(encryptConfig.obtenerEncriptador().encode(registerRequestDTO.getPassword()))
                .username(registerRequestDTO.getUsername())
                .build();

    }
}
