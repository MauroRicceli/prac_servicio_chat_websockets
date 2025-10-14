package com.chatapp.chatapppractice.models.entities;

import com.chatapp.chatapppractice.models.constants.PostConstants;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "posts")
public class PostEntity {

    @Id
    private String id;

    @NotBlank
    private String idUserOwner;

    @Email
    @NotBlank
    private String emailOwner;

    @NotBlank
    @Size(min = PostConstants.MIN_SIZE_POST_CONTENT, max = PostConstants.MAX_SIZE_POST_CONTENT)
    private String postContent;

    @NotBlank
    private String usernameOwner;

    @Min(0)
    private int likesAmount;

    @NotNull
    private Set<UserLike> userLikes;

    @Min(0)
    private int commentsAmount;

    @NotNull
    private Instant dateCreation;
    @NotNull
    private Instant dateLastModification;

    @NotNull
    private Set<UserComment> userComments;
}
