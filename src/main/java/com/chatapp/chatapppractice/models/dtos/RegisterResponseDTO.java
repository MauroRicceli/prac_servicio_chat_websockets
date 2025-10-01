package com.chatapp.chatapppractice.models.dtos;

import com.chatapp.chatapppractice.models.enums.UserRole;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class RegisterResponseDTO {
    @NotNull
    private Long id;
    @NotNull
    private String email;
    @NotNull
    private UserRole userRole;
    @NotNull
    private String username;
    @NotNull
    private String accessToken;
    @NotNull
    private String refreshToken;

}
