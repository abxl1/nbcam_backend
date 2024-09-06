package com.sparta.schedulerjpa.service;

import com.sparta.schedulerjpa.dto.commentDto.requestDto.CommentSaveRequestDto;
import com.sparta.schedulerjpa.dto.commentDto.requestDto.CommentUpdateRequestDto;
import com.sparta.schedulerjpa.dto.commentDto.responseDto.CommentDetailResponseDto;
import com.sparta.schedulerjpa.dto.commentDto.responseDto.CommentSaveResponseDto;
import com.sparta.schedulerjpa.dto.commentDto.responseDto.CommentSimpleResponseDto;
import com.sparta.schedulerjpa.dto.commentDto.responseDto.CommentUpdateResponseDto;
import com.sparta.schedulerjpa.entity.Comment;
import com.sparta.schedulerjpa.entity.Schedule;
import com.sparta.schedulerjpa.repository.CommentRepository;
import com.sparta.schedulerjpa.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;

    // post
    @Transactional
    public CommentSaveResponseDto saveComment(Long scheduleId, CommentSaveRequestDto commentSaveRequestDto) {

        Schedule schedule = scheduleRepository
                .findById(scheduleId)
                .orElseThrow(() -> new NullPointerException("해당 일정이 없습니다."));

        Comment comment = new Comment(
                commentSaveRequestDto.getCommentUserName(),
                commentSaveRequestDto.getCommentContents(),
                schedule
        );
        Comment addComment = commentRepository.save(comment);
        return new CommentSaveResponseDto(addComment);
    }

    // get all
    public List<CommentSimpleResponseDto> getComments() {
        List<Comment> comments = commentRepository.findAll();
        List<CommentSimpleResponseDto> list = new ArrayList<>();

        for (Comment comment : comments) {
            list.add(new CommentSimpleResponseDto(comment.getCommentUserName(), comment.getCommentContents()));
        }
        return list;
    }

    // get one
    public CommentDetailResponseDto getComment(Long commentId) {

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NullPointerException("해당 댓글이 없습니다."));
        return new CommentDetailResponseDto(comment);
    }

    // put
    @Transactional
    public CommentUpdateResponseDto updateComment(Long commentId, CommentUpdateRequestDto commentUpdateRequestDto) {

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NullPointerException("해당 댓글이 없습니다."));
        comment.commentUpdate(commentUpdateRequestDto.getCommentUserName(), commentUpdateRequestDto.getCommentContents());
        return new CommentUpdateResponseDto(comment);
    }

    // delete
    @Transactional
    public void deleteComment(Long commentId) {

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NullPointerException("해당 댓글이 없습니다."));
        commentRepository.delete(comment);
    }
}
