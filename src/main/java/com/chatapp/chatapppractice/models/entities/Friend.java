package com.chatapp.chatapppractice.models.entities;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Instant;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Friend {

    private String id;

    @NotNull
    @Email
    private String email;

    @NotNull
    private String username;

    private Instant friendshipStarted;

    @Override
    public boolean equals(final Object object) {
        if (object instanceof Friend) {
            Friend aux = (Friend) object;
            return Objects.equals(aux.getEmail(), this.email) && Objects.equals(aux.getUsername(), this.username);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, username);
    }

}
