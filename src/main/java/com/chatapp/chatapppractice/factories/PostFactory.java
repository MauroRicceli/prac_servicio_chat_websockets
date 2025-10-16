package com.chatapp.chatapppractice.factories;

import com.chatapp.chatapppractice.models.dtos.userinteractiondtos.CreatePostDTO;
import com.chatapp.chatapppractice.models.entities.PostEntity;
import com.chatapp.chatapppractice.models.entities.auxiliars.UserComment;
import com.chatapp.chatapppractice.models.entities.UserEntity;
import com.chatapp.chatapppractice.models.entities.auxiliars.UserLike;

import java.time.Instant;
import java.util.HashSet;

public final class PostFactory {

    private PostFactory() { };

    /**
     * Creates a new PostEntity with all the needed data.
     * @param user Entity with active useractions info.
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

    /**
     * Creates a new UserLike with all the needed data.
     * @param user Entity with active user info.
     * @return UserLike with the active user needed info.
     */
    public static UserLike createUserLike(final UserEntity user) {
        return UserLike.builder()
                .id(user.getId().toString())
                .email(user.getEmail())
                .username(user.getUsername())
                .whenLiked(Instant.now())
                .build();
    }
}
