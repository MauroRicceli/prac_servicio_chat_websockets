package com.chatapp.chatapppractice.controllers.useractions;

import com.chatapp.chatapppractice.models.dtos.userinteractiondtos.FriendshipResponseDTO;
import com.chatapp.chatapppractice.services.userinteraction.UserFriendshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class FriendshipController {

    /**
     * Service with all the logic needed to manage friendships.
     */
    private final UserFriendshipService userFriendshipService;

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
}
