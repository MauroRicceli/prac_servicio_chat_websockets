package com.chatapp.chatapppractice.services.userinteraction;

import com.chatapp.chatapppractice.factories.PostFactory;
import com.chatapp.chatapppractice.mapper.PostMapper;
import com.chatapp.chatapppractice.models.constants.ErrorMessagesConstants;
import com.chatapp.chatapppractice.models.dtos.userinteractiondtos.CreatePostDTO;
import com.chatapp.chatapppractice.models.dtos.userinteractiondtos.PostResponseDTO;
import com.chatapp.chatapppractice.models.entities.PostEntity;
import com.chatapp.chatapppractice.models.entities.UserEntity;
import com.chatapp.chatapppractice.repositories.PostRepository;
import com.chatapp.chatapppractice.security.exceptions.PostDoesntExistsException;
import com.chatapp.chatapppractice.security.exceptions.UnauthorizedActionOnPostException;
import com.chatapp.chatapppractice.services.utils.PostVerificationService;
import com.chatapp.chatapppractice.services.utils.UserVerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserPostService {

    /**
     * Service with all the methods needed to manage user actions verifications.
     */
    private final UserVerificationService userVerificationService;
    /**
     * Service with all the methods needed to manage post verifications.
     */
    private final PostVerificationService postVerificationService;

    /**
     * Repository of the posts.
     */
    private final PostRepository postRepository;

    /**
     * This method creates a new post associated with the active useractions.
     * @param createPostDTO with all the needed info.
     * @return DTO with the data of the created post.
     */
    public PostResponseDTO createPost(final CreatePostDTO createPostDTO) {

        UserEntity user = userVerificationService.obtainUserEntityFromSecurityContext();

        PostEntity newPost = PostFactory.createPostEntity(user, createPostDTO);

        postRepository.save(newPost);

        return PostMapper.postEntityToPostResponseDTO(newPost);

    }

    /**
     * This method deletes the selected post if it belongs to the active useractions.
     * @param idPost id of the selected post to delete.
     * @throws UnauthorizedActionOnPostException if the active useractions isn't the owner of the post.
     * @throws PostDoesntExistsException if the post doesn't exist.
     * @return DTO with the data of the deleted post.
     */
    public PostResponseDTO deletePost(final String idPost) {

        UserEntity user = userVerificationService.obtainUserEntityFromSecurityContext();

        PostEntity post = postVerificationService.verifyPostExistenceAndGetIt(idPost);

        //If it's not the owner, throws an exception.
        postVerificationService.verifyPostOwnership(post, user);

        postRepository.delete(post);

        return PostMapper.postEntityToPostResponseDTO(post);
    }

    /**
     * This method modifies the selected post if it belongs to the active useractions.
     * @param idPost id of the post wanted to modify.
     * @param modifyPostDTO DTO with the data to modify that post.
     * @throws UnauthorizedActionOnPostException if the active useractions isn't the owner of the post.
     * @throws PostDoesntExistsException if the post doesn't exist.
     * @return DTO with the data of the new modified post
     */
    public PostResponseDTO modifyPost(final String idPost, final CreatePostDTO modifyPostDTO) {

        UserEntity user = userVerificationService.obtainUserEntityFromSecurityContext();

        PostEntity post = postVerificationService.verifyPostExistenceAndGetIt(idPost);

        //If it's not the owner, throws an exception.
        postVerificationService.verifyPostOwnership(post, user);

        post.setPostContent(modifyPostDTO.getPostContent());
        post.setDateLastModification(Instant.now());

        postRepository.save(post);

        return PostMapper.postEntityToPostResponseDTO(post);

    }

    /**
     * Gets all the posts of the active useractions.
     * @return a list of DTOs with every post info.
     */
    public List<PostResponseDTO> getUserPosts() {

        UserEntity user = userVerificationService.obtainUserEntityFromSecurityContext();

        List<PostEntity> userPosts = postRepository.findByIdUserOwner(user.getId().toString());
        List<PostResponseDTO> ret = new ArrayList<PostResponseDTO>();

        userPosts.forEach(post -> ret.add(PostMapper.postEntityToPostResponseDTO(post)));

        return ret;

    }

    /**
     * Gets the post with that id.
     * @param postId id of the post wanted to get.
     * @throws PostDoesntExistsException if the post doesn't exist.
     * @return DTO with the post info.
     */
    public PostResponseDTO getPost(final String postId) {

        PostEntity post = postVerificationService.verifyPostExistenceAndGetIt(postId);

        return PostMapper.postEntityToPostResponseDTO(post);
    }

    /**
     * Gets all the posts of the app.
     * @return List of DTOs with every post info.
     */
    public List<PostResponseDTO> getAllPosts() {
        List<PostEntity> posts = postRepository.findAll();
        List<PostResponseDTO> ret = new ArrayList<PostResponseDTO>();

        posts.forEach(post -> ret.add(PostMapper.postEntityToPostResponseDTO(post)));

        return ret;
    }
}
