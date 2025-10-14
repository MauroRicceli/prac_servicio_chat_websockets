package com.chatapp.chatapppractice.models.dtos.authdtos;

import com.chatapp.chatapppractice.models.constants.UserValidationsConstants;
import com.chatapp.chatapppractice.models.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@ToString
@AllArgsConstructor
@Builder
@Getter
@Setter
public class RegisterRequestDTO {

    @NotNull
    @Size(min = UserValidationsConstants.MIN_SIZE_REAL_NAMES_USER, max = UserValidationsConstants.MAX_SIZE_REAL_NAMES_USER)
    private String firstname;

    @NotNull
    @Size(min = UserValidationsConstants.MIN_SIZE_REAL_NAMES_USER, max = UserValidationsConstants.MAX_SIZE_REAL_NAMES_USER)
    private String lastname;

    @NotNull
    @Email
    private String email;

    private String password;

    @NotNull
    private UserRole userRole;

    @NotNull
    private String username;

}
