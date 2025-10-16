package com.chatapp.chatapppractice.models.entities.auxiliars;

import com.chatapp.chatapppractice.models.constants.PostConstants;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserComment {

    @NotBlank
    private String id;

    @NotBlank
    private String userOwnerId;

    @NotBlank
    private String username;

    @NotBlank
    @Size(min = PostConstants.MIN_SIZE_COMMENT, max = PostConstants.MAX_SIZE_COMMENT)
    private String comment;

    @Min(0)
    private int likesAmount;

    @NotNull
    private Set<UserLike> userLikes;

    @Override
    public boolean equals(final Object object) {
        if (object instanceof UserComment) {
            UserComment aux = (UserComment) object;
            return Objects.equals(aux.getId(), this.id)
                    && Objects.equals(aux.getUserOwnerId(), this.userOwnerId)
                    && Objects.equals(aux.getComment(), this.comment) && Objects.equals(aux.getUsername(), this.username);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userOwnerId, username, comment);
    }

}
