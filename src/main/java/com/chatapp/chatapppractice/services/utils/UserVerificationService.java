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
     * Verify useractions existence in the DB and gets it.
     * @param email of the useractions.
     * @throws UserDoesntExistsException if the useractions isn't registered
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
     * Verify useractions existence in the DB and gets it.
     * @param id of the useractions.
     * @throws UserDoesntExistsException if the useractions isn't registered
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
     * Verify useractions existence in the DB.
     * @param email of the useractions.
     * @return true or false if it doesn't exist.
     */
    public boolean verifyUserExistenceByEmail(final String email) {

        Optional<UserEntity> user = userRepository.findByEmail(email);
        return user.isPresent();
    }

    /**
     * Verify useractions existence in the DB.
     * @param id of the useractions.
     * @return true or false if it doesn't exist.
     */
    public boolean verifyUserExistenceByID(final Long id) {
        Optional<UserEntity> user = userRepository.findById(id);
        return user.isPresent();
    }

    /**
     * Obtains the useractions entity of the DB associated with the one in the security context.
     * @return the entity or exception if it doesn't exist somehow.
     */
    public UserEntity obtainUserEntityFromSecurityContext() {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return verifyUserExistenceFromEmailAndGetIt(user.getUsername());

    }
}
