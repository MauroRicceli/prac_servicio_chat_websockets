package com.chatapp.chatapppractice.services.userinteraction;

import com.chatapp.chatapppractice.factories.ResponseFactory;
import com.chatapp.chatapppractice.factories.UserFactory;
import com.chatapp.chatapppractice.models.constants.ErrorMessagesConstants;
import com.chatapp.chatapppractice.models.dtos.userinteractiondtos.FriendshipResponseDTO;
import com.chatapp.chatapppractice.models.entities.Friend;
import com.chatapp.chatapppractice.models.entities.FriendshipEntity;
import com.chatapp.chatapppractice.models.entities.UserEntity;
import com.chatapp.chatapppractice.repositories.FriendshipRepository;
import com.chatapp.chatapppractice.security.exceptions.UserDoesntExistsException;
import com.chatapp.chatapppractice.services.utils.UserVerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserFriendshipService {
    /**
     * Repository of the friendships.
     */
    private final FriendshipRepository friendshipRepository;
    /**
     * Factory to create new types of users.
     */
    private final UserFactory userFactory;

    /**
     * Service with utils for user management.
     */
    private final UserVerificationService userVerificationService;

    /**
     * This method adds a new friend if they are not already, and removes the friendship if they were.
     * @param idSelectedUser id of the added user.
     * @throws UserDoesntExistsException if the user trying to manage doesn't exist.
     * @return DTO with info.
     */
    public FriendshipResponseDTO manageFriends(final Long idSelectedUser) {
        if (!userVerificationService.verifyUserExistenceByID(idSelectedUser)) {
            throw new UserDoesntExistsException(ErrorMessagesConstants.USER_DOESNT_EXISTS);
        }

        UserEntity user = userVerificationService.obtainUserEntityFromSecurityContext();

        FriendshipEntity friendships = friendshipRepository.findByEmailOwner(user.getEmail());

        //si es la primera amistad de todas.
        if (friendships == null) {
           return addFriend(idSelectedUser, userFactory.userEntityToFriendship(user), user);
        }

        if (friendships.getFriendList().stream().anyMatch(friend -> friend.getId().equals(idSelectedUser.toString()))) {
            return removeFriend(idSelectedUser, friendships, user);
        }
        return addFriend(idSelectedUser, friendships, user);

    }

    /**
     * This method adds a new friend to the active user.
     * @param idSelectedUser id of the added user.
     * @param friendships friendships of the active user.
     * @param userOwner active user.
     * @return DTO with info.
     */
    public FriendshipResponseDTO addFriend(final Long idSelectedUser, final FriendshipEntity friendships, final UserEntity userOwner) {

        Friend newFriend = userFactory.userEntityToFriend(userVerificationService.verifyUserExistenceFromIDAndGetIt(idSelectedUser));
        friendships.getFriendList().add(newFriend);
        friendshipRepository.save(friendships);

        return ResponseFactory.createFriendshipResponse(userOwner, friendships);
    }

    /**
     * This method removes an existing friendship from the active user.
     * @param idSelectedUser id of the removed user.
     * @param friendships friendships of the active user.
     * @param userOwner active user.
     * @return DTO with info.
     */
    public FriendshipResponseDTO removeFriend(final Long idSelectedUser, final FriendshipEntity friendships, final UserEntity userOwner) {

        friendships.getFriendList().removeIf(friend -> friend.getId().equals(idSelectedUser.toString()));
        friendshipRepository.save(friendships);

        return ResponseFactory.createFriendshipResponse(userOwner, friendships);
    }
}
