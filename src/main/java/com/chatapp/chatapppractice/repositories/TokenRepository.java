package com.chatapp.chatapppractice.repositories;

import com.chatapp.chatapppractice.models.entities.TokenEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<TokenEntity, Long> {

    /**
     * This method invalidated all the tokens owned by a user, using his email.
     * @param email of the user.
     */
    @Modifying
    @Transactional
    @Query("UPDATE TokenEntity t SET t.revoked = true, t.expired = true WHERE t.userOwner.email =:ownerEmail")
    void invalidateTokensByUserEmail(@Param("ownerEmail") String email);

    /**
     * This method invalidates the token sended.
     * @param token wanted to invalidate.
     */
    @Modifying
    @Transactional
    @Query("UPDATE TokenEntity t SET t.revoked = true, t.expired = true WHERE t.token =:token")
    void invalidateToken(@Param("token") String token);

    /**
     * This method obtains the token entity with this token.
     * @param token of the entity.
     * @return Optional with the entity inside, or an empty Optional.
     */
    Optional<TokenEntity> findByToken(String token);

}
