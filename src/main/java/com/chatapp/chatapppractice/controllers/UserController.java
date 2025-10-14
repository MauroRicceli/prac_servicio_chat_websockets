package com.chatapp.chatapppractice.controllers;

import com.chatapp.chatapppractice.models.dtos.userinteractiondtos.FriendshipResponseDTO;
import com.chatapp.chatapppractice.services.userinteraction.UserFriendshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

    /**
     * Service with all the logic needed to manage friendships.
     */
    private final UserFriendshipService userFriendshipService;

    /**
     * Adds or removes a selected friend from the active user.
     * @param selectedUserID if of the selected user for the action.
     * @return DTO with info.
     */
    @PostMapping("/friend/{selectedUserID}")
    public ResponseEntity<FriendshipResponseDTO> manageFriend(final @PathVariable Long selectedUserID) {
        return new ResponseEntity<>(userFriendshipService.manageFriends(selectedUserID), HttpStatus.CREATED);
    }

}
