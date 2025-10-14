package com.chatapp.chatapppractice.models.dtos.userinteractiondtos;

import com.chatapp.chatapppractice.models.constants.PostConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreatePostDTO {

    @NotBlank
    @Size(min = PostConstants.MIN_SIZE_POST_CONTENT, max = PostConstants.MAX_SIZE_POST_CONTENT)
    private String postContent;

}
