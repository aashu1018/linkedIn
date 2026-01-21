package com.linkedIn.posts_service.controller;

import com.linkedIn.posts_service.dto.PostCreateRequestDTO;
import com.linkedIn.posts_service.dto.PostDTO;
import com.linkedIn.posts_service.service.PostsService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostsController {

    private final PostsService postsService;

    @PostMapping
    private ResponseEntity<PostDTO> createPost(@RequestBody PostCreateRequestDTO postDTO, HttpServletRequest httpServletRequest){
        PostDTO createdPost = postsService.createPost(postDTO, 1L);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }
}
