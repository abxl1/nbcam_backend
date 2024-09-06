package com.sparta.schedulerjpa.repository;

import com.sparta.schedulerjpa.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
