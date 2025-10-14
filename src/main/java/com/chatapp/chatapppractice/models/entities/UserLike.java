package com.chatapp.chatapppractice.models.entities;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLike {

    @NotBlank
    private String id;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String username;

    @NotNull
    private Instant whenLiked;
}
