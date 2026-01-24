package com.linkedIn.posts_service.controller;

import com.linkedIn.posts_service.service.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
public class LikesController {

    private final PostLikeService postLikeService;

    @PostMapping("/{postId}")
    private ResponseEntity<Void> likePost(@PathVariable Long postId){
        postLikeService.likePost(postId, 1L);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{postId}")
    private ResponseEntity<Void> unlikePost(@PathVariable Long postId){
        postLikeService.unlikePost(postId, 1L);
        return ResponseEntity.noContent().build();
    }

}
