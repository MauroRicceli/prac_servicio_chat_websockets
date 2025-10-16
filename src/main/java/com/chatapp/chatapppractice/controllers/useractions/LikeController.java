package com.chatapp.chatapppractice.controllers.useractions;

import com.chatapp.chatapppractice.models.dtos.userinteractiondtos.LikeDTO;
import com.chatapp.chatapppractice.models.dtos.userinteractiondtos.PostResponseDTO;
import com.chatapp.chatapppractice.models.utils.Tuple;
import com.chatapp.chatapppractice.services.userinteraction.UserLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @PutMapping("/user/posts/{postId}/likes")
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
    @PutMapping("/user/posts/{postId}/comments/{commentId}/likes")
    public ResponseEntity<PostResponseDTO> manageLikeOnComment(final @PathVariable String postId, final @PathVariable String commentId) {
        Tuple<PostResponseDTO, HttpStatus> response = userLikeService.manageLikeOnComment(postId, commentId);
        return new ResponseEntity<>(response.getFirstObject(), response.getSecondObject());
    }

    /**
     * Gets all the likes related to the selected post.
     * @param postId id of the post wanted to see his likes.
     * @return list of DTOs with every like info.
     */
    @GetMapping("/posts/{postId}/likes")
    public ResponseEntity<List<LikeDTO>> getPostLikes(final @PathVariable String postId) {
        return new ResponseEntity<>(userLikeService.getPostLikes(postId), HttpStatus.OK);
    }

    /**
     * Gets all the likes related to a comment in the selected post.
     * @param postId id of the post that contains that comment.
     * @param commentId id of the comment wanted to see his likes.
     * @return list of DTOs with every like info.
     */
    @GetMapping("/posts/{postId}/comments/{commentId}/likes")
    public ResponseEntity<List<LikeDTO>> getPostCommentLikes(final @PathVariable String postId, final @PathVariable String commentId) {
        return new ResponseEntity<>(userLikeService.getPostCommentLikes(postId, commentId), HttpStatus.OK);
    }
}
