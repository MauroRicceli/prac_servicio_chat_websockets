package com.chatapp.chatapppractice.controllers;

import com.chatapp.chatapppractice.models.dtos.LoginRequestDTO;
import com.chatapp.chatapppractice.models.dtos.RegisterRequestDTO;
import com.chatapp.chatapppractice.models.dtos.AuthResponseDTO;
import com.chatapp.chatapppractice.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<AuthResponseDTO> register(@RequestBody final RegisterRequestDTO registerRequestDTO) {
        return new ResponseEntity<>(authService.register(registerRequestDTO), HttpStatus.CREATED);
    }

    /**
     * Receives an DTO with the needed user data, logins the user and refresh his tokens if exists and the credentials matches.
     * @param loginRequestDTO with the needed user data.
     * @return DTO with the user info and refreshed tokens.
     */
    @PostMapping(value = "/login", consumes = "application/json", produces = "application/json")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody final LoginRequestDTO loginRequestDTO) {
        return new ResponseEntity<>(authService.login(loginRequestDTO), HttpStatus.OK);
    }
}
