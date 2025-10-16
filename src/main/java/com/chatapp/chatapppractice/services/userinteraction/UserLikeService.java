package com.chatapp.chatapppractice.services.userinteraction;

import com.chatapp.chatapppractice.factories.PostFactory;
import com.chatapp.chatapppractice.mapper.PostMapper;
import com.chatapp.chatapppractice.models.dtos.userinteractiondtos.LikeDTO;
import com.chatapp.chatapppractice.models.dtos.userinteractiondtos.PostResponseDTO;
import com.chatapp.chatapppractice.models.entities.PostEntity;
import com.chatapp.chatapppractice.models.entities.UserEntity;
import com.chatapp.chatapppractice.models.entities.auxiliars.UserComment;
import com.chatapp.chatapppractice.models.entities.auxiliars.UserLike;
import com.chatapp.chatapppractice.models.utils.Tuple;
import com.chatapp.chatapppractice.repositories.PostRepository;
import com.chatapp.chatapppractice.security.exceptions.CommentDoesntExistsException;
import com.chatapp.chatapppractice.security.exceptions.PostDoesntExistsException;
import com.chatapp.chatapppractice.services.utils.PostVerificationService;
import com.chatapp.chatapppractice.services.utils.UserVerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserLikeService {

    /**
     * Service with all the methods needed to manage user actions verifications.
     */
    private final UserVerificationService userVerificationService;

    /**
     * Service with all the methods needed to manage post verifications.
     */
    private final PostVerificationService postVerificationService;

    /**
     * Repository of all the posts.
     */
    private final PostRepository postRepository;

    /**
     * This method creates a new like on the post, or removes it if it already exists for the active user.
     * @param postId id of the post wanted to like.
     * @throws PostDoesntExistsException if the post of that id doesn't exist.
     * @return Tuple containing the DTO of the post, and the action performed as HttpStatus.
     */
    public Tuple<PostResponseDTO, HttpStatus> manageLikeOnPost(final String postId) {

        UserEntity user = userVerificationService.obtainUserEntityFromSecurityContext();

        PostEntity post = postVerificationService.verifyPostExistenceAndGetIt(postId);

        UserLike newLike = PostFactory.createUserLike(user);

        //to know if it was already liked or not.
        if (post.getUserLikes().add(newLike)) {

            post.setLikesAmount(post.getLikesAmount() + 1);
            postRepository.save(post);

            return new Tuple<PostResponseDTO, HttpStatus>(PostMapper.postEntityToPostResponseDTO(post), HttpStatus.CREATED);
        } else {

            post.getUserLikes().remove(newLike);
            post.setLikesAmount(post.getLikesAmount() - 1);
            postRepository.save(post);

            return new Tuple<PostResponseDTO, HttpStatus>(PostMapper.postEntityToPostResponseDTO(post), HttpStatus.NO_CONTENT);
        }

    }

    /**
     * This method creates a new like on the comment of that post, or removes it if it already exists for the active user.
     * @param postId id of the post that contains that comment.
     * @param commentId id of the comment wanted to manage.
     * @throws PostDoesntExistsException if the post of that id doesn't exist.
     * @throws CommentDoesntExistsException if the comment doesn't exist in that post.
     * @return Tuple containing the DTO of the post, and the action performed as HttpStatus.
     */
    public Tuple<PostResponseDTO, HttpStatus> manageLikeOnComment(final String postId, final String commentId) {

        UserEntity user = userVerificationService.obtainUserEntityFromSecurityContext();

        PostEntity post = postVerificationService.verifyPostExistenceAndGetIt(postId);

        UserLike newLike = PostFactory.createUserLike(user);

        UserComment comment = postVerificationService.verifyPostCommentExistenceAndGetIt(post, commentId);

        //to know if the comment was already liked or not.
        //could improve on the repetition but i think this way is more legible.
        if (comment.getUserLikes().add(newLike)) {

            comment.setLikesAmount(comment.getLikesAmount() + 1);
            post.getUserComments().remove(comment);
            post.getUserComments().add(comment);

            postRepository.save(post);
            return new Tuple<>(PostMapper.postEntityToPostResponseDTO(post), HttpStatus.CREATED);
        } else {

            comment.getUserLikes().remove(newLike);
            comment.setLikesAmount(comment.getLikesAmount() - 1);
            post.getUserComments().remove(comment);
            post.getUserComments().add(comment);

            postRepository.save(post);
            return new Tuple<>(PostMapper.postEntityToPostResponseDTO(post), HttpStatus.NO_CONTENT);
        }
    }

    /**
     * This method gets all the likes of the selected post.
     * @param postId id of the selected post.
     * @throws PostDoesntExistsException if the post doesn't exist.
     * @return a List of DTO with every like info.
     */
    public List<LikeDTO> getPostLikes(final String postId) {
        PostEntity post = postVerificationService.verifyPostExistenceAndGetIt(postId);

        List<LikeDTO> postLikes = new ArrayList<>();

        post.getUserLikes().forEach(userLike -> postLikes.add(PostMapper.userLikeToLikeDTO(userLike)));

        return postLikes;
    }

    /**
     * This method gets all the likes of the selected comment in the selected post.
     * @param postId id of the post that contains that comment.
     * @param commentId id of the comment wanted to see his likes.
     * @throws PostDoesntExistsException if the post doesn't exist.
     * @throws CommentDoesntExistsException if the comment doesn't exist.
     * @return list of DTOs with every like info.
     */
    public List<LikeDTO> getPostCommentLikes(final String postId, final String commentId) {
        PostEntity post = postVerificationService.verifyPostExistenceAndGetIt(postId);

        UserComment comment = postVerificationService.verifyPostCommentExistenceAndGetIt(post, commentId);

        List<LikeDTO> commentLikes = new ArrayList<>();

        comment.getUserLikes().forEach(userLike -> commentLikes.add(PostMapper.userLikeToLikeDTO(userLike)));

        return commentLikes;
    }
}
