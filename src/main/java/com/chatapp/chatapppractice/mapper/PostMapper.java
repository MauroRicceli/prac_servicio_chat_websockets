package com.chatapp.chatapppractice.mapper;

import com.chatapp.chatapppractice.models.dtos.userinteractiondtos.LikeDTO;
import com.chatapp.chatapppractice.models.dtos.userinteractiondtos.PostResponseDTO;
import com.chatapp.chatapppractice.models.entities.PostEntity;
import com.chatapp.chatapppractice.models.entities.auxiliars.UserLike;

public final class PostMapper {

    private PostMapper() { };

    /**
     * Maps an PostEntity to an PostResponseDTO.
     * @param post Entity with the info.
     * @return DTO with that info.
     */
    public static PostResponseDTO postEntityToPostResponseDTO(final PostEntity post) {
        return PostResponseDTO.builder()
                .id(post.getId())
                .idUserOwner(post.getIdUserOwner())
                .postContent(post.getPostContent())
                .emailOwner(post.getEmailOwner())
                .commentsAmount(post.getCommentsAmount())
                .likesAmount(post.getLikesAmount())
                .userComments(post.getUserComments())
                .userLikes(post.getUserLikes())
                .usernameOwner(post.getUsernameOwner())
                .dateCreation(post.getDateCreation())
                .dateLastModification(post.getDateLastModification())
                .build();
    }

    /**
     * Maps an UserLike to an PostLikeDTO.
     * @param userLike Entity with the info.
     * @return DTO with the info.
     */
    public static LikeDTO userLikeToLikeDTO(final UserLike userLike) {
        return LikeDTO.builder()
                .id(userLike.getId())
                .username(userLike.getUsername())
                .whenLiked(userLike.getWhenLiked())
                .build();
    }
}
