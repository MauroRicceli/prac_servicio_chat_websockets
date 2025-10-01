package com.chatapp.chatapppractice.controllers;

import com.chatapp.chatapppractice.models.dtos.RegisterDTO;
import com.chatapp.chatapppractice.models.dtos.RegisterResponseDTO;
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
     * @param registerDTO with the needed user data
     * @return DTO with the user info and the generated tokens.
     */
    @PostMapping(value = "/register", consumes = "application/json", produces = "application/json")
    public ResponseEntity<RegisterResponseDTO> register(@RequestBody final RegisterDTO registerDTO) {
        System.out.println(registerDTO);
        return new ResponseEntity<>(authService.register(registerDTO), HttpStatus.CREATED);
    }

}
