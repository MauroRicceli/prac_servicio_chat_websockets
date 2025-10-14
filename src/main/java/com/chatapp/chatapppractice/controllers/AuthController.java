package com.chatapp.chatapppractice.controllers;

import com.chatapp.chatapppractice.models.dtos.authdtos.LoginRequestDTO;
import com.chatapp.chatapppractice.models.dtos.authdtos.RegisterRequestDTO;
import com.chatapp.chatapppractice.models.dtos.authdtos.AuthResponseDTO;
import com.chatapp.chatapppractice.services.authentication.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    /**
     * Service with all the methods used in this controller.
     */
    private final AuthService authService;

    /**
     * Receives an DTO with the needed user data, registers the user if it doesn't exist and create his tokens.
     * @param registerRequestDTO with the needed user data
     * @return DTO with the user info and the generated tokens.
     */
    @PostMapping(value = "/register", consumes = "application/json", produces = "application/json")
    public ResponseEntity<AuthResponseDTO> register(@Valid @RequestBody final RegisterRequestDTO registerRequestDTO) {
        return new ResponseEntity<>(authService.register(registerRequestDTO), HttpStatus.CREATED);
    }

    /**
     * Receives an DTO with the needed user data, logins the user and refresh his tokens if exists and the credentials matches.
     * @param loginRequestDTO with the needed user data.
     * @return DTO with the user info and refreshed tokens.
     */
    @PostMapping(value = "/login", consumes = "application/json", produces = "application/json")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody final LoginRequestDTO loginRequestDTO) {
        return new ResponseEntity<>(authService.login(loginRequestDTO), HttpStatus.OK);
    }

    /**
     * Receives an authorization header with the token and the prefix. <br>
     * Refreshes the tokens if it's everything okay. <br>
     * Throws and exception if somemthing occurs.
     * @param authHeader with the prefix and token.
     * @return DTO with the user info and refreshed tokens.
     */
    @PreAuthorize("hasAnyRole('STANDARD', 'ADMIN')")
    @GetMapping(value = "/refresh-tokens", produces = "application/json")
    public ResponseEntity<AuthResponseDTO> refreshToken(@RequestHeader(HttpHeaders.AUTHORIZATION) final String authHeader) {
        return new ResponseEntity<>(authService.refreshToken(authHeader), HttpStatus.OK);
    }
}
