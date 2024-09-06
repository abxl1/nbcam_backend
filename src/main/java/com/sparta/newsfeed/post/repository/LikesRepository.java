package com.sparta.newsfeed.post.repository;

import com.sparta.newsfeed.post.entity.Comment;
import com.sparta.newsfeed.post.entity.Likes;
import com.sparta.newsfeed.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface LikesRepository extends JpaRepository<Likes,Long> {
    List<Likes> findByPost(Post post);

    List<Likes> findByComment(Comment comment);
}
