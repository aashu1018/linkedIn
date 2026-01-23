package com.linkedIn.posts_service.controller;

import com.linkedIn.posts_service.service.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/like")
@RequiredArgsConstructor
public class LikesController {

    private PostLikeService postLikeService;

    @PostMapping("/{postId}")
    private ResponseEntity<Void> likePost(@PathVariable Long postId){
        postLikeService.likePost(postId, 1L);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{postId}")
    private ResponseEntity<Void> unlikePost(@PathVariable Long postId){
        postLikeService.unlikePost(postId, 1L);
        return ResponseEntity.noContent().build();
    }

}
