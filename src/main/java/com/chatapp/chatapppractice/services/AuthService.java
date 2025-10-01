package com.chatapp.chatapppractice.services;

import com.chatapp.chatapppractice.mapper.UserMapper;
import com.chatapp.chatapppractice.models.dtos.RegisterDTO;
import com.chatapp.chatapppractice.models.dtos.RegisterResponseDTO;
import com.chatapp.chatapppractice.models.entities.TokenEntity;
import com.chatapp.chatapppractice.models.entities.UserEntity;
import com.chatapp.chatapppractice.repositories.TokenRepository;
import com.chatapp.chatapppractice.repositories.UserRepository;
import com.chatapp.chatapppractice.security.exceptions.UserAlreadyRegisteredException;
import com.chatapp.chatapppractice.security.exceptions.UserDoesntExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthService {

    /**
     * Repository of the registered users.
     */
    private final UserRepository userRepository;
    /**
     * Repository of the created tokens of every user.
     */
    private final TokenRepository tokenRepository;
    /**
     * Utility to convert the user between DTO and Entity.
     */
    private final UserMapper userMapper;
    /**
     *  Service to manage JWT Tokens.
     */
    private final JWTService jwtService;

    /**
     * Verify user existence in the DB and gets it.
     * @param email of the user.
     * @throws UserDoesntExistsException if the user isn't registered
     * @return UserEntity
     */
    private UserEntity verifyUserExistenceAndGetIt(final String email) {

        Optional<UserEntity> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new UserDoesntExistsException("User is not registered in the database");
        }
        return user.get();
    }

    /**
     * Verify user existence in the DB.
     * @param email of the user.
     * @return true or false if it doesn't exist.
     */
    private boolean verifyUserExistence(final String email) {

        Optional<UserEntity> user = userRepository.findByEmail(email);
        return user.isPresent();
    }

    /**
     * Registers the user in registerDTO if it doesn't exist already
     * @param registerDTO with data of the user
     * @throws UserAlreadyRegisteredException if the user it's already registered
     * @return DTO with information
     */
    public RegisterResponseDTO register(final RegisterDTO registerDTO) {

        if (verifyUserExistence(registerDTO.getEmail())) {
            throw new UserAlreadyRegisteredException("The user " + registerDTO.getEmail() + " is already registered in the app");
        }

        UserEntity user = userMapper.registerDTOToUser(registerDTO);

        userRepository.save(user);

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        TokenEntity tkn = TokenEntity.builder()
                .token(refreshToken)
                .revoked(false)
                .expirated(false)
                .userOwner(user)
                .build();

        System.out.println(tkn);

        tokenRepository.save(tkn);

        return RegisterResponseDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .userRole(user.getUserRole())
                .username(user.getUsername())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

}
