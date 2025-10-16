package com.chatapp.chatapppractice.models.entities.auxiliars;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank
    @Email
    private String email;

    @NotBlank
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
