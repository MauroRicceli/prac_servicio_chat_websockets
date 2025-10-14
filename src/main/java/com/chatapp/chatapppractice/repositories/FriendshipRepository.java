package com.chatapp.chatapppractice.repositories;

import com.chatapp.chatapppractice.models.entities.FriendshipEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

public interface FriendshipRepository extends MongoRepository<FriendshipEntity, String> {

    /**
     * Finds the document of the user friends by his email.
     * @return Entity representing that document.
     */
    FriendshipEntity findByEmailOwner(String emailOwner);
}
