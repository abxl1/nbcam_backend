package com.sparta.schedulerjpa.controller;

import com.sparta.schedulerjpa.dto.commentDto.requestDto.CommentSaveRequestDto;
import com.sparta.schedulerjpa.dto.commentDto.requestDto.CommentUpdateRequestDto;
import com.sparta.schedulerjpa.dto.commentDto.responseDto.CommentDetailResponseDto;
import com.sparta.schedulerjpa.dto.commentDto.responseDto.CommentSaveResponseDto;
import com.sparta.schedulerjpa.dto.commentDto.responseDto.CommentSimpleResponseDto;
import com.sparta.schedulerjpa.dto.commentDto.responseDto.CommentUpdateResponseDto;
import com.sparta.schedulerjpa.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    public final CommentService commentService;

    // post
    @PostMapping("/schedules/{scheduleId}/comments")
    public ResponseEntity<CommentSaveResponseDto> addComment(@PathVariable Long scheduleId, @RequestBody CommentSaveRequestDto commentSaveRequestDto) {
        return ResponseEntity.ok(commentService.saveComment(scheduleId, commentSaveRequestDto));
    }

    // get all
    @GetMapping("/schedules/comments")
    public ResponseEntity<List<CommentSimpleResponseDto>> getComments() {
        return ResponseEntity.ok(commentService.getComments());
    }

    // get one
    @GetMapping("/schedules/comments/{commentId}")
    public ResponseEntity<CommentDetailResponseDto> getComment(@PathVariable Long commentId) {
        return ResponseEntity.ok(commentService.getComment(commentId));
    }

    // put
    @PutMapping("/schedules/comments/{commentId}")
    public ResponseEntity<CommentUpdateResponseDto> updateComment(@PathVariable Long commentId, @RequestBody CommentUpdateRequestDto commentUpdateRequestDto) {
        return ResponseEntity.ok(commentService.updateComment(commentId, commentUpdateRequestDto));
    }

    // delete
    @DeleteMapping("/schedules/comments/{commentId}")
    public void deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
    }
}
