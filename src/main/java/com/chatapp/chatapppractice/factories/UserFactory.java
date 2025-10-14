package com.chatapp.chatapppractice.factories;

import com.chatapp.chatapppractice.configs.EncryptConfig;
import com.chatapp.chatapppractice.models.auxiliars.Auth2UserInfo;
import com.chatapp.chatapppractice.models.dtos.authdtos.RegisterRequestDTO;
import com.chatapp.chatapppractice.models.entities.Friend;
import com.chatapp.chatapppractice.models.entities.FriendshipEntity;
import com.chatapp.chatapppractice.models.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.HashSet;

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
                .authModifiedUsername(true)
                .auth2User(false)
                .build();

    }

    /**
     * Creates the user entity with the auth2UserInfo data.
     * @param auth2UserInfo Class with the user data.
     * @return UserEntity created with that data.
     */
    public UserEntity auth2UserInfoToUserEntity(final Auth2UserInfo auth2UserInfo) {

        return UserEntity.builder()
                .firstname(auth2UserInfo.getFirstname())
                .lastname(auth2UserInfo.getLastname())
                .fullname(auth2UserInfo.getFullname())
                .email(auth2UserInfo.getEmail())
                .verifiedEmail(auth2UserInfo.isVerifiedEmail())
                .userRole(auth2UserInfo.getUserRole())
                .password(auth2UserInfo.getPassword())
                .username(auth2UserInfo.getFullname())
                .auth2User(true)
                .build();
    }

    /**
     * Creates a friend class with the user entity data.
     * @param userEntity Entity with the user data.
     * @return Friend class created with the data.
     */
    public Friend userEntityToFriend(final UserEntity userEntity) {
        return Friend.builder()
                .id(userEntity.getId().toString())
                .email(userEntity.getEmail()).username(userEntity.getUsername()).friendshipStarted(Instant.now())
                .build();
    }

    /**
     * Creates a friendship entity with the user entity data.
     * @param userEntity Entity with the user data.
     * @return Friendship entity with the data.
     */
    public FriendshipEntity userEntityToFriendship(final UserEntity userEntity) {
        return FriendshipEntity.builder()
                .emailOwner(userEntity.getEmail())
                .usernameOwner(userEntity.getUsername())
                .friendList(new HashSet<Friend>())
                .build();
    }
}
