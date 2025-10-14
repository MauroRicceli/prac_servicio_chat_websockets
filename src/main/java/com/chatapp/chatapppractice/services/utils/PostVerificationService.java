package com.chatapp.chatapppractice.services.utils;

import com.chatapp.chatapppractice.models.constants.ErrorMessagesConstants;
import com.chatapp.chatapppractice.models.constants.PostConstants;
import com.chatapp.chatapppractice.models.entities.PostEntity;
import com.chatapp.chatapppractice.models.entities.UserEntity;
import com.chatapp.chatapppractice.models.enums.UserRole;
import com.chatapp.chatapppractice.repositories.PostRepository;
import com.chatapp.chatapppractice.security.exceptions.PostDoesntExistsException;
import com.chatapp.chatapppractice.security.exceptions.UnauthorizedActionOnPostException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostVerificationService {

    /**
     * Repository of the posts.
     */
    private final PostRepository postRepository;

    /**
     * Obtains the post with that id.
     * @param postId id of the post wanted to get.
     * @return Entity of the post.
     */
    public PostEntity verifyPostExistenceAndGetIt(final String postId) {

        return postRepository.findById(postId).orElseThrow(() -> new PostDoesntExistsException(ErrorMessagesConstants.POST_DOESNT_EXISTS));

    }

    /**
     * Verifies that the owner of the post it's the user in the argument.
     * If the user's an ADMIN, bypasses the verification.
     * @param post wanted to verify.
     * @param user wanted to verify ownership.
     * @throws UnauthorizedActionOnPostException if it's not his owner.
     */
    public void verifyPostOwnership(final PostEntity post, final UserEntity user) {
        if (user.getUserRole().equals(UserRole.ADMIN)) {
            return;
        }

        if (!post.getIdUserOwner().equals(user.getId().toString())) {
            throw new UnauthorizedActionOnPostException(ErrorMessagesConstants.UNAUTHORIZED_ON_POST_ACTION);
        }
    }
}
