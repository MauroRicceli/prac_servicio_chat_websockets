package com.chatapp.chatapppractice.services;

import com.chatapp.chatapppractice.configs.EncryptConfig;
import com.chatapp.chatapppractice.models.constants.ErrorMessages;
import com.chatapp.chatapppractice.models.dtos.LoginRequestDTO;
import com.chatapp.chatapppractice.models.dtos.RegisterRequestDTO;
import com.chatapp.chatapppractice.models.dtos.AuthResponseDTO;
import com.chatapp.chatapppractice.models.entities.UserEntity;
import com.chatapp.chatapppractice.factories.ResponseFactory;
import com.chatapp.chatapppractice.factories.TokenFactory;
import com.chatapp.chatapppractice.factories.UserFactory;
import com.chatapp.chatapppractice.repositories.TokenRepository;
import com.chatapp.chatapppractice.repositories.UserRepository;
import com.chatapp.chatapppractice.security.exceptions.UserAlreadyRegisteredException;
import com.chatapp.chatapppractice.security.exceptions.UserDoesntExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
     *  Service to manage JWT Tokens.
     */
    private final JWTService jwtService;
    /**
     * Used to authenticate the users.
     */
    private final AuthenticationManager authenticationManager;
    /**
     * Utility to create users from arguments or DTOs.
     */
    private final UserFactory userFactory;

    /**
     * Verify user existence in the DB and gets it.
     * @param email of the user.
     * @throws UserDoesntExistsException if the user isn't registered
     * @return UserEntity
     */
    private UserEntity verifyUserExistenceAndGetIt(final String email) {

        Optional<UserEntity> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new UserDoesntExistsException(ErrorMessages.USER_DOESNT_EXISTS);
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
     * Registers the user in registerDTO if it doesn't exist already.
     * @param registerRequestDTO with data of the user.
     * @throws UserAlreadyRegisteredException if the user it's already registered.
     * @return DTO with information
     */
    public AuthResponseDTO register(final RegisterRequestDTO registerRequestDTO) {

        if (verifyUserExistence(registerRequestDTO.getEmail())) {
            throw new UserAlreadyRegisteredException(ErrorMessages.USER_ALREADY_REGISTERED);
        }

        UserEntity user = userFactory.registerDTOToUserEntity(registerRequestDTO);

        userRepository.save(user);

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        tokenRepository.save(TokenFactory.createTokenEntity(refreshToken, user));

        return ResponseFactory.createAuthResponse(user, refreshToken, accessToken);
    }

    /**
     * Logs in the user in the DTO if it exists and his credentials matches.
     * @param loginRequestDTO with data of the user.
     * @return DTO with information
     */
    public AuthResponseDTO login(final LoginRequestDTO loginRequestDTO) {

        UserEntity user = verifyUserExistenceAndGetIt(loginRequestDTO.getEmail());

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDTO.getEmail(), loginRequestDTO.getPassword()));

        tokenRepository.invalidateTokensByUserEmail(user.getEmail());

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        tokenRepository.save(TokenFactory.createTokenEntity(refreshToken, user));

        return ResponseFactory.createAuthResponse(user, refreshToken, accessToken);
    }

}
