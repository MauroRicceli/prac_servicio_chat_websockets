package com.chatapp.chatapppractice.repositories;

import com.chatapp.chatapppractice.models.entities.PostEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PostRepository extends MongoRepository<PostEntity, String> {

    List<PostEntity> findByIdUserOwner(String idUserOwner);
}
