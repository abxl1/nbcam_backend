package com.sparta.trelloproject.domain.comment.controller;


import com.sparta.trelloproject.domain.auth.entity.AuthUser;
import com.sparta.trelloproject.domain.comment.dto.request.CommentRequest;
import com.sparta.trelloproject.domain.comment.dto.response.CommentResponse;
import com.sparta.trelloproject.domain.comment.service.CommentService;
import com.sparta.trelloproject.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cards/{cardId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

//    댓글 작성
    @PostMapping
    public ResponseEntity<CommentResponse> saveComment(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable Long cardId,
            @RequestBody CommentRequest commentRequest) {
        CommentResponse responseDto = commentService.saveComment(authUser,cardId,commentRequest);
        return ResponseEntity.ok(responseDto);
    }

//    댓글 수정
    @PatchMapping("/{commentId}")
    public ResponseEntity<CommentResponse> updateComment(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable Long cardId,
            @PathVariable Long commentId,
            @RequestBody CommentRequest commentRequest) {
        CommentResponse responseDto = commentService.updateComment(authUser, commentId, commentRequest);
        return ResponseEntity.ok(responseDto);
    }

//    댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable Long cardId,
            @PathVariable Long commentId) {
        commentService.deleteComment(authUser, commentId);
        {

        }
        return ResponseEntity.noContent().build();
    }
}
