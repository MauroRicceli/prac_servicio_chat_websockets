package com.chatapp.chatapppractice.services;

import com.chatapp.chatapppractice.models.Auth2UserInfo;
import com.chatapp.chatapppractice.models.constants.ErrorMessagesConstants;
import com.chatapp.chatapppractice.models.dtos.LoginRequestDTO;
import com.chatapp.chatapppractice.models.dtos.RegisterRequestDTO;
import com.chatapp.chatapppractice.models.dtos.AuthResponseDTO;
import com.chatapp.chatapppractice.models.entities.TokenEntity;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.lang.constant.Constable;

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
     * Service to manage user verifications in db.
     */
    private final UserVerificationService userVerificationService;

    /**
     * Registers the user in registerDTO if it doesn't exist already.
     * @param registerRequestDTO with data of the user.
     * @throws UserAlreadyRegisteredException if the user it's already registered.
     * @return DTO with information
     */
    public AuthResponseDTO register(final RegisterRequestDTO registerRequestDTO) {

        if (userVerificationService.verifyUserExistence(registerRequestDTO.getEmail())) {
            throw new UserAlreadyRegisteredException(ErrorMessagesConstants.USER_ALREADY_REGISTERED);
        }

        UserEntity user = userFactory.registerDTOToUserEntity(registerRequestDTO);

        userRepository.save(user);

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        tokenRepository.save(TokenFactory.createTokenEntity(refreshToken, user));

        return ResponseFactory.createAuthResponse(user, refreshToken, accessToken);
    }

    /**
     * Registers a user in the database with oAuth2 data.
     * @param auth2UserInfo Class with the user data.
     * @return DTO with information.
     */
    public AuthResponseDTO oAuth2Register(final Auth2UserInfo auth2UserInfo) {

        UserEntity user = userFactory.auth2UserInfoToUserEntity(auth2UserInfo);

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

        UserEntity user = userVerificationService.verifyUserExistenceAndGetIt(loginRequestDTO.getEmail());

        if (user.isAuth2User()) {
            throw new UserDoesntExistsException(ErrorMessagesConstants.PASSWORD_DOESNT_MATCH); //enrealidad es de oAuth2, pero no informar de eso en UX.
        }

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDTO.getEmail(), loginRequestDTO.getPassword()));

        tokenRepository.invalidateTokensByUserEmail(user.getEmail());

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        tokenRepository.save(TokenFactory.createTokenEntity(refreshToken, user));

        return ResponseFactory.createAuthResponse(user, refreshToken, accessToken);
    }

    /**
     * Logs in with user info using oAuth2.
     * @param auth2UserInfo Data of the user.
     * @return DTO with information.
     */
    public AuthResponseDTO oAuth2Login(final Auth2UserInfo auth2UserInfo) {
        UserEntity user = userRepository.findByEmail(auth2UserInfo.getEmail()).orElseThrow();

        tokenRepository.invalidateTokensByUserEmail(user.getEmail());

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        tokenRepository.save(TokenFactory.createTokenEntity(refreshToken, user));

        return ResponseFactory.createAuthResponse(user, refreshToken, accessToken);
    }

    /**
     * Refreshes the tokens of the user in the refresh token granted if
     * its valid, invalidates the old ones.
     * @param authHeader header with the refresh token of the user.
     * @return DTO with information
     */
    public AuthResponseDTO refreshToken(final String authHeader) {
        UserEntity user = userVerificationService.obtainUserEntityFromSecurityContext();

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        tokenRepository.invalidateTokensByUserEmail(user.getEmail());

        tokenRepository.save(TokenFactory.createTokenEntity(refreshToken, user));

        return ResponseFactory.createAuthResponse(user, refreshToken, accessToken);
    }

}
