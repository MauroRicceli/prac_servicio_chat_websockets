package com.chatapp.chatapppractice.factories;

import com.chatapp.chatapppractice.models.dtos.userinteractiondtos.CreatePostDTO;
import com.chatapp.chatapppractice.models.entities.PostEntity;
import com.chatapp.chatapppractice.models.entities.UserComment;
import com.chatapp.chatapppractice.models.entities.UserEntity;
import com.chatapp.chatapppractice.models.entities.UserLike;

import java.time.Instant;
import java.util.HashSet;

public final class PostFactory {

    private PostFactory() { };

    /**
     * Creates a new PostEntity with all the needed data.
     * @param user Entity with active user info.
     * @param createPostDTO DTO with all the post content info.
     * @return Entity created with that data.
     */
    public static PostEntity createPostEntity(final UserEntity user, final CreatePostDTO createPostDTO) {
        return PostEntity.builder()
                .idUserOwner(user.getId().toString())
                .emailOwner(user.getEmail())
                .usernameOwner(user.getUsername())
                .postContent(createPostDTO.getPostContent())
                .userComments(new HashSet<UserComment>())
                .commentsAmount(0)
                .userLikes(new HashSet<UserLike>())
                .likesAmount(0)
                .dateCreation(Instant.now())
                .dateLastModification(Instant.now())
                .build();

    }
}
