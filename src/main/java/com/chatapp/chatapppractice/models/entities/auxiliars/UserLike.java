package com.chatapp.chatapppractice.models.entities.auxiliars;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Instant;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLike {

    @NotBlank
    private String id;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String username;

    @NotNull
    private Instant whenLiked;

    @Override
    public boolean equals(final Object object) {
        if (object instanceof UserLike) {
            UserLike aux = (UserLike) object;
            return Objects.equals(aux.getId(), this.id) && Objects.equals(aux.getUsername(), this.username) && Objects.equals(aux.getEmail(), this.email);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, username);
    }
}
