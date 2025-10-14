package com.chatapp.chatapppractice.services.utils;

import com.chatapp.chatapppractice.models.constants.ErrorMessagesConstants;
import com.chatapp.chatapppractice.models.entities.UserEntity;
import com.chatapp.chatapppractice.repositories.UserRepository;
import com.chatapp.chatapppractice.security.exceptions.UserDoesntExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserVerificationService {

    /**
     * Repository of the registered users.
     */
    private final UserRepository userRepository;

    /**
     * Verify user existence in the DB and gets it.
     * @param email of the user.
     * @throws UserDoesntExistsException if the user isn't registered
     * @return UserEntity with his data.
     */
    public UserEntity verifyUserExistenceFromEmailAndGetIt(final String email) {

        Optional<UserEntity> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new UserDoesntExistsException(ErrorMessagesConstants.USER_DOESNT_EXISTS);
        }
        return user.get();
    }

    /**
     * Verify user existence in the DB and gets it.
     * @param id of the user.
     * @throws UserDoesntExistsException if the user isn't registered
     * @return UserEntity with his data.
     */
    public UserEntity verifyUserExistenceFromIDAndGetIt(final Long id) {

        Optional<UserEntity> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserDoesntExistsException(ErrorMessagesConstants.USER_DOESNT_EXISTS);
        }
        return user.get();
    }

    /**
     * Verify user existence in the DB.
     * @param email of the user.
     * @return true or false if it doesn't exist.
     */
    public boolean verifyUserExistenceByEmail(final String email) {

        Optional<UserEntity> user = userRepository.findByEmail(email);
        return user.isPresent();
    }

    /**
     * Verify user existence in the DB.
     * @param id of the user.
     * @return true or false if it doesn't exist.
     */
    public boolean verifyUserExistenceByID(final Long id) {
        Optional<UserEntity> user = userRepository.findById(id);
        return user.isPresent();
    }

    /**
     * Obtains the user entity of the DB associated with the one in the security context.
     * @return the entity or exception if it doesn't exist somehow.
     */
    public UserEntity obtainUserEntityFromSecurityContext() {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return verifyUserExistenceFromEmailAndGetIt(user.getUsername());

    }
}
