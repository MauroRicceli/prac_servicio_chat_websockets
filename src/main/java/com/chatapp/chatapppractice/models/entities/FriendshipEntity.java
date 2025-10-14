package com.chatapp.chatapppractice.models.entities;

import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document(collection = "friendships")
public class FriendshipEntity {

    @Id
    private String id;

    @NotNull
    @Email
    @Indexed(unique = true)
    private String emailOwner;

    @NotNull
    private String usernameOwner;

    private Set<Friend> friendList = new HashSet<Friend>();
}
