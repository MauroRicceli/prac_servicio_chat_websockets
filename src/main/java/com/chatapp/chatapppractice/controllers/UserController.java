package com.chatapp.chatapppractice.controllers;

import com.chatapp.chatapppractice.models.dtos.userinteractiondtos.CreatePostDTO;
import com.chatapp.chatapppractice.models.dtos.userinteractiondtos.FriendshipResponseDTO;
import com.chatapp.chatapppractice.models.dtos.userinteractiondtos.PostResponseDTO;
import com.chatapp.chatapppractice.services.userinteraction.UserFriendshipService;
import com.chatapp.chatapppractice.services.userinteraction.UserPostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

    /**
     * Service with all the logic needed to manage friendships.
     */
    private final UserFriendshipService userFriendshipService;
    /**
     * Service with all the logic needed to manage posts, comments and likes.
     */
    private final UserPostService userPostService;

    /**
     * Adds or removes a selected friend from the active user.
     * @param selectedUserID if of the selected user for the action.
     * @return DTO with info.
     */
    @PreAuthorize("hasAnyRole('STANDARD', 'ADMIN')")
    @PostMapping("/friend/{selectedUserID}")
    public ResponseEntity<FriendshipResponseDTO> manageFriend(final @PathVariable Long selectedUserID) {
        return new ResponseEntity<>(userFriendshipService.manageFriends(selectedUserID), HttpStatus.CREATED);
    }

    /**
     * Creates a new post associated with the active user.
     * @param createPostDTO with the needed info.
     * @return DTO with info.
     */
    @PreAuthorize("hasAnyRole('STANDARD', 'ADMIN')")
    @PostMapping("/post")
    public ResponseEntity<PostResponseDTO> createPost(final @Valid @RequestBody CreatePostDTO createPostDTO) {
        return new ResponseEntity<>(userPostService.createPost(createPostDTO), HttpStatus.CREATED);
    }

    /**
     * Deletes the selected post if it belongs to the active user.
     * @param postId id of the selected post.
     * @return DTO with info.
     */
    @PreAuthorize("hasAnyRole('STANDARD', 'ADMIN')")
    @DeleteMapping("/post/{postId}")
    public ResponseEntity<PostResponseDTO> deletePost(final @PathVariable String postId) {
        return new ResponseEntity<>(userPostService.deletePost(postId), HttpStatus.NO_CONTENT);
    }

    /**
     * Modifies the selected post if it belongs to the active user.
     * @param postId id of the selected post.
     * @param modifiedPostDTO with the needed info wanted to modify.
     * @return DTO with info.
     */
    @PreAuthorize("hasAnyRole('STANDARD', 'ADMIN')")
    @PutMapping("/post/{postId}")
    public ResponseEntity<PostResponseDTO> modifyPost(final @PathVariable String postId, final @RequestBody CreatePostDTO modifiedPostDTO) {
        return new ResponseEntity<>(userPostService.modifyPost(postId, modifiedPostDTO), HttpStatus.OK);
    }

    /**
     * Gets all the post of the active user.
     * @return List of DTOs with every post info.
     */
    @PreAuthorize("hasAnyRole('STANDARD', 'ADMIN')")
    @GetMapping("/post")
    public ResponseEntity<List<PostResponseDTO>> getUserPosts() {
        return new ResponseEntity<>(userPostService.getUserPosts(), HttpStatus.OK);
    }

    /**
     * Gets the selected post if it's exist.
     * @param postId of the selected post.
     * @return DTO with post info.
     */
    @PreAuthorize("hasAnyRole('STANDARD', 'ADMIN')")
    @GetMapping("/post/{postId}")
    public ResponseEntity<PostResponseDTO> getPost(final @PathVariable String postId) {
        return new ResponseEntity<>(userPostService.getPost(postId), HttpStatus.OK);
    }

    /**
     * Gets all the posts of the app. Only for admins.
     * @return List of DTOs with every post info.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/post/all")
    public ResponseEntity<List<PostResponseDTO>> getAllPosts() {
        return new ResponseEntity<>(userPostService.getAllPosts(), HttpStatus.OK);
    }
}
