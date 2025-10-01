package com.chatapp.chatapppractice.mapper;

import com.chatapp.chatapppractice.configs.EncryptConfig;
import com.chatapp.chatapppractice.models.dtos.RegisterDTO;
import com.chatapp.chatapppractice.models.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserMapper {

    /**
     * Used to encrypt the password before converting the registerDTO to UserEntity.
     */
    private final EncryptConfig encryptConfig;

    /**
     * Creates the user entity with the registerDTO data, hashes the password.
     * @param registerDTO DTO with the user data.
     * @return UserEntity created with that data.
     */
    public UserEntity registerDTOToUser(final RegisterDTO registerDTO) {

        final String hashedPw = encryptConfig.obtenerEncriptador().encode(registerDTO.getPassword());

        return UserEntity.builder()
                .firstname(registerDTO.getFirstname())
                .lastname(registerDTO.getLastname())
                .fullname(registerDTO.getFirstname() + " " + registerDTO.getLastname())
                .userRole(registerDTO.getUserRole())
                .email(registerDTO.getEmail())
                .password(hashedPw)
                .username(registerDTO.getUsername())
                .build();

    }
}
