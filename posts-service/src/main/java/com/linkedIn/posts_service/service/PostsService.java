package com.linkedIn.posts_service.service;

import com.linkedIn.posts_service.dto.PostCreateRequestDTO;
import com.linkedIn.posts_service.dto.PostDTO;
import com.linkedIn.posts_service.entity.Post;
import com.linkedIn.posts_service.exception.ResourceNotFoundException;
import com.linkedIn.posts_service.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostsService {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    public PostDTO createPost(PostCreateRequestDTO postDTO, Long userId) {
        Post post = modelMapper.map(postDTO, Post.class);
        post.setUserId(userId);

        Post savedPost = postRepository.save(post);

        return modelMapper.map(savedPost, PostDTO.class);
    }

    public PostDTO getPostById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + postId));
        return modelMapper.map(post, PostDTO.class);

    }

    public List<PostDTO> getAllPostsOfUser(Long userId) {
        List<Post> posts = postRepository.findByUserId(userId);
        return posts.stream()
                .map((ele) -> modelMapper.map(ele, PostDTO.class))
                .toList();
    }
}
