package com.linkedIn.posts_service.service;

import com.linkedIn.posts_service.entity.Post;
import com.linkedIn.posts_service.entity.PostLike;
import com.linkedIn.posts_service.exception.BadRequestException;
import com.linkedIn.posts_service.exception.ResourceNotFoundException;
import com.linkedIn.posts_service.repository.PostLikeRepository;
import com.linkedIn.posts_service.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostLikeService {

    private PostLikeRepository postLikeRepository;
    private PostRepository postRepository;
    private ModelMapper modelMapper;

    public void likePost(Long postId, Long userId) {

        log.info("Attempting to like the post with id: {}", postId);

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post with id: " + postId + " does not exist"));

        boolean alreadyLiked = postLikeRepository.existsByPostIdAndUserId(postId, userId);
        if(alreadyLiked){
            throw new BadRequestException("The post has already been liked!");
        }

        PostLike postLike = new PostLike();
        postLike.setPostId(postId);
        postLike.setUserId(userId);
        postLikeRepository.save(postLike);
        log.info("Post with id: {} liked successfully", postId);

    }

    public void unlikePost(Long postId, Long userId) {
        log.info("Attempting to unlike the post with id: {}", postId);

        boolean exists = postLikeRepository.existsById(postId);
        if(!exists){
            throw new ResourceNotFoundException("Post with id: " + postId + " is not present");
        }

        boolean alreadyUnliked = postLikeRepository.existsByPostIdAndUserId(postId, userId);
        if(alreadyUnliked){
            throw new BadRequestException("The post has already been unliked!");
        }

        postLikeRepository.deleteByUserIdAndPostId(userId, postId);

        log.info("Post with id: {} unliked successfully", postId);
    }
}
