package com.chatapp.chatapppractice.models.dtos.userinteractiondtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class LikeDTO {
    @NotBlank
    private String id;

    @NotBlank
    private String username;

    @NotNull
    private Instant whenLiked;
}
