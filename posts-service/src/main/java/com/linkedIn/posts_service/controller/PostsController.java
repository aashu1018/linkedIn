package com.linkedIn.posts_service.controller;

import com.linkedIn.posts_service.dto.PostCreateRequestDTO;
import com.linkedIn.posts_service.dto.PostDTO;
import com.linkedIn.posts_service.service.PostsService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/core")
@RequiredArgsConstructor
public class PostsController {

    private final PostsService postsService;

    @PostMapping
    private ResponseEntity<PostDTO> createPost(@RequestBody PostCreateRequestDTO postDTO, HttpServletRequest httpServletRequest){
        PostDTO createdPost = postsService.createPost(postDTO, 1L);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    @GetMapping("/{postId}")
    private ResponseEntity<PostDTO> getPost(@PathVariable Long postId){
        PostDTO post = postsService.getPostById(postId);
        return ResponseEntity.ok(post);
    }

    @GetMapping("/users/{userId}/allPosts")
    private ResponseEntity<List<PostDTO>> getAllPosts(@PathVariable Long userId){
        List<PostDTO> posts = postsService.getAllPostsOfUser(userId);
        return ResponseEntity.ok(posts);
    }
}
