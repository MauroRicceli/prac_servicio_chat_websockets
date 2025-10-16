package com.chatapp.chatapppractice.controllers.useractions;

import com.chatapp.chatapppractice.models.dtos.userinteractiondtos.PostResponseDTO;
import com.chatapp.chatapppractice.models.utils.Tuple;
import com.chatapp.chatapppractice.services.userinteraction.UserLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class LikeController {

    /**
     * Service with all the logic needed to manage likes.
     */
    private final UserLikeService userLikeService;

    /**
     * Creates a new like on the post or removes it if it was already liked by the active user.
     * @param postId id of the post wanted to like.
     * @return DTO with info of the liked post.
     */
    @PutMapping("/post/{postId}/like")
    public ResponseEntity<PostResponseDTO> manageLikeOnPost(final @PathVariable String postId) {

        Tuple<PostResponseDTO, HttpStatus> response = userLikeService.manageLikeOnPost(postId);

        return new ResponseEntity<>(response.getFirstObject(), response.getSecondObject());
    }

    /**
     * Creates a new like on the comment of the post or removes it if it was already likes by the active user.
     * @param postId id of the post that contains that comment.
     * @param commentId id of the comment wanted to like.
     * @return DTO with info of the post that contains that like.
     */
    @PutMapping("/post/{postId}/{commentId}/like")
    public ResponseEntity<PostResponseDTO> manageLikeOnComment(final @PathVariable String postId, final @PathVariable String commentId) {
        Tuple<PostResponseDTO, HttpStatus> response = userLikeService.manageLikeOnComment(postId, commentId);
        return new ResponseEntity<>(response.getFirstObject(), response.getSecondObject());
    }
}
