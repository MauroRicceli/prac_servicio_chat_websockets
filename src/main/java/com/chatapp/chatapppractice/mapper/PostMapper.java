package com.chatapp.chatapppractice.mapper;

import com.chatapp.chatapppractice.models.dtos.userinteractiondtos.PostResponseDTO;
import com.chatapp.chatapppractice.models.entities.PostEntity;

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
}
