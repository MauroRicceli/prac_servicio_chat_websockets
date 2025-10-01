package com.chatapp.chatapppractice.models.dtos;

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
public class RegisterDTO {

    @NotNull
    @Size(min = 2)
    private String firstname;

    @NotNull
    @Size(min = 2)
    private String lastname;

    @NotNull
    @Email
    private String email;

    @NotNull
    private String password;

    @NotNull
    private UserRole userRole;

    @NotNull
    private String username;
}
