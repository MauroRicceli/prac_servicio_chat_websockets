package com.chatapp.chatapppractice.models.dtos.userinteractiondtos;

import com.chatapp.chatapppractice.models.entities.auxiliars.UserComment;
import com.chatapp.chatapppractice.models.entities.auxiliars.UserLike;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class PostResponseDTO {

    @Id
    private String id;

    @NotBlank
    private String idUserOwner;

    @Email
    @NotBlank
    private String emailOwner;

    @NotBlank
    private String postContent;

    @NotBlank
    private String usernameOwner;

    private int likesAmount;

    @NotNull
    private Set<UserLike> userLikes;

    private int commentsAmount;

    @NotNull
    private Instant dateCreation;
    @NotNull
    private Instant dateLastModification;

    @NotNull
    private Set<UserComment> userComments;
}
