package com.chatapp.chatapppractice.models.entities;

import com.chatapp.chatapppractice.models.constants.PostConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserComment {

    @NotBlank
    private String id;

    @NotBlank
    private String username;

    @NotBlank
    @Size(min = PostConstants.MIN_SIZE_COMMENT, max = PostConstants.MAX_SIZE_COMMENT)
    private String comment;
}
