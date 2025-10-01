package com.chatapp.chatapppractice.repositories;

import com.chatapp.chatapppractice.models.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    /**
     * Obtains the registered User from the database with that email;
     * @param email of the registered user.
     * @return Optional with the user inside, or empty if it doesn't exist.
     */
    Optional<UserEntity> findByEmail(String email);

}
