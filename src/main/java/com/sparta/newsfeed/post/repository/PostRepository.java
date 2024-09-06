package com.sparta.newsfeed.post.repository;

import com.sparta.newsfeed.profile.entity.User;
import com.sparta.newsfeed.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByUserInOrderByCreatedAtDesc(List<User> userList, Pageable pageable);

    Page<Post> findByUserInOrderByLikesDesc(List<User> userList, Pageable pageable);

    Page<Post> findByUserInOrderByModifiedAtDesc(List<User> userList, Pageable pageable);

    Page<Post> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
}
