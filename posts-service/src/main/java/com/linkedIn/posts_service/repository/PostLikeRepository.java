package com.linkedIn.posts_service.repository;

import com.linkedIn.posts_service.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    boolean existsByPostIdAndUserId(Long postId, Long userId);

    void deleteByUserIdAndPostId(Long userId, Long postId);
}
