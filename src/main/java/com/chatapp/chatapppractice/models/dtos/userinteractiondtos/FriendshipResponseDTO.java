package com.chatapp.chatapppractice.models.dtos.userinteractiondtos;

import com.chatapp.chatapppractice.models.entities.auxiliars.Friend;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class FriendshipResponseDTO {
    /**
     * id of the owner of the friendships.
     */
    private String id;
    /**
     * email of the owner of the friendships.
     */
    private String email;
    /**
     * Set of the friendships of the useractions.
     */
    private Set<Friend> friends;
}
