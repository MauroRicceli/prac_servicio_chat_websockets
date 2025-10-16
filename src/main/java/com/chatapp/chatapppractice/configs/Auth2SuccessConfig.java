package com.chatapp.chatapppractice.configs;

import com.chatapp.chatapppractice.models.utils.Auth2UserInfo;
import com.chatapp.chatapppractice.models.dtos.authdtos.AuthResponseDTO;

import com.chatapp.chatapppractice.models.enums.UserRole;
import com.chatapp.chatapppractice.services.authentication.AuthService;
import com.chatapp.chatapppractice.services.utils.UserVerificationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
@Component
@RequiredArgsConstructor
public class Auth2SuccessConfig implements AuthenticationSuccessHandler {
    private final AuthService authService; // tu servicio de persistencia
    private final UserVerificationService userVerifService;

    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request,
                                        final HttpServletResponse response,
                                        final Authentication authentication) throws IOException {

        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        Map<String, Object> attributes = oauthToken.getPrincipal().getAttributes();


        String email = (String) attributes.get("email");
        String fullname = (String) attributes.get("name");
        String firstname = (String) attributes.get("given_name");
        String lastname = (String) attributes.get("family_name");
        boolean verifiedEmail = (boolean) attributes.get("email_verified");

        Auth2UserInfo userInfo = Auth2UserInfo.builder()
                .userRole(UserRole.STANDARD)
                .email(email)
                .firstname(firstname)
                .lastname(lastname)
                .fullname(fullname)
                .verifiedEmail(verifiedEmail).password(null)
                .build();


        AuthResponseDTO authResponseDTO;

        if (userVerifService.verifyUserExistenceByEmail(email)) {
            authResponseDTO = authService.oAuth2Login(userInfo);
        } else {
            authResponseDTO = authService.oAuth2Register(userInfo);
        }

        String json = String.format(
                "{" + "\"id\": %d, "
                        + "\"email\": \"%s\", "
                        + "\"userRole\": \"%s\", "
                        + "\"username\": \"%s\", "
                        + "\"accessToken\": \"%s\", "
                        + "\"refreshToken\": \"%s\""
                        + "}",
                authResponseDTO.getId(),
                authResponseDTO.getEmail(),
                authResponseDTO.getUserRole(),
                authResponseDTO.getUsername(),
                authResponseDTO.getAccessToken(),
                authResponseDTO.getRefreshToken()
        );

        response.setContentType("application/json");
        response.getWriter().write(json);
    }
}

