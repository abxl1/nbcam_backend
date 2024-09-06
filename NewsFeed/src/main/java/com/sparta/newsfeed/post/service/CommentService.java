package com.sparta.newsfeed.post.service;

import com.sparta.newsfeed.profile.entity.User;
import com.sparta.newsfeed.post.dto.commentDto.CommentRequestDto;
import com.sparta.newsfeed.post.dto.commentDto.CommentResponseDto;
import com.sparta.newsfeed.post.entity.Comment;
import com.sparta.newsfeed.post.entity.Likes;
import com.sparta.newsfeed.post.entity.Post;
import com.sparta.newsfeed.post.exception.AuthorizedCheckException;
import com.sparta.newsfeed.post.repository.CommentRepository;
import com.sparta.newsfeed.post.repository.LikesRepository;
import com.sparta.newsfeed.post.repository.PostRepository;
import com.sparta.newsfeed.profile.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final ProfileRepository userRepository;
    private final LikesRepository likesRepository;


    //id로 post찾기
    public Post findPost(Long postsId) {
        Post post = postRepository.findById(postsId).orElseThrow(NullPointerException::new);
        return post;
    }

    //id로 유저찾기
    public User findUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(NullPointerException::new);
        return user;
    }

    @Transactional
    public CommentResponseDto saveComments(Long postsId, CommentRequestDto commentRequestDto, Long userId) {
        Post post = findPost(postsId);
        User user = findUser(userId);
        Comment comment = new Comment(commentRequestDto.getCommentContents(), post, user);
        Comment savedComment = commentRepository.save(comment);
        return new CommentResponseDto(savedComment);
    }

    public List<CommentResponseDto> getCommentList(Long postsId) {
        Post post = findPost(postsId);
        List<Comment> commentList = post.getCommentList();
        List<CommentResponseDto> dtoList = new ArrayList<>();
        for (Comment c : commentList) {
            CommentResponseDto dto = new CommentResponseDto(c);
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Transactional
    public CommentResponseDto updateComment(Long postsId, Long commentsId, CommentRequestDto commentRequestDto, Long userId) {
        Post post = findPost(postsId);
        Comment comment = commentRepository.findById(commentsId).orElseThrow(NullPointerException::new);
        User user = findUser(userId);

        if (comment.getPost().equals(post) || comment.getUser().equals(user)) {
            comment.update(commentRequestDto.getCommentContents());
        } else {
            throw new AuthorizedCheckException("not allowed");
        }
        return new CommentResponseDto(comment);
    }

    @Transactional
    public void deleteComment(Long postsId, Long commentsId, Long userId) {
        Post post = findPost(postsId);
        Comment comment = commentRepository.findById(commentsId).orElseThrow(NullPointerException::new);
        User user = findUser(userId);

        if (comment.getPost().getUser().equals(post.getUser()) || comment.getUser().equals(user)) {
            commentRepository.delete(comment);
        } else {
            throw new AuthorizedCheckException("not allowed");
        }
    }
    @Transactional
    public CommentResponseDto likeComment(Long postsId, Long commentsId, Long userId) {
        Post post = findPost(postsId);
        Comment comment = commentRepository.findById(commentsId).orElseThrow(NullPointerException::new);
        User user = findUser(userId);
        List<Likes> checkLikes = likesRepository.findByComment(comment);
        if (!comment.getPost().equals(post)) {
            throw new NullPointerException("wrong input");
        }
        for (Likes l : checkLikes) {
            if (l.getUser().equals(user)) {
                throw new AuthorizedCheckException("already liked it");
            }
        }
        if (comment.getUser().equals(user)) {
            throw new RuntimeException("not allowed to like yourself");
        }
        Likes likes = new Likes(user, post, comment);
        likesRepository.save(likes);
        return new CommentResponseDto(comment);
    }
    @Transactional
    public void deleteLikeComment(Long postsId, Long commentsId, Long likesId, Long userId) {
        Post post = findPost(postsId);
        Comment comment = commentRepository.findById(commentsId).orElseThrow(NullPointerException::new);
        User user = findUser(userId);
        Likes likes = likesRepository.findById(likesId).orElseThrow(NullPointerException::new);
        if (!likes.getComment().equals(comment) || !likes.getComment().getPost().equals(post)) {
            throw new NullPointerException("wrong input");
        }
        if (likes.getUser().equals(user)) {
            likesRepository.delete(likes);
        } else {
            throw new AuthorizedCheckException("not allowed");
        }


    }
}
