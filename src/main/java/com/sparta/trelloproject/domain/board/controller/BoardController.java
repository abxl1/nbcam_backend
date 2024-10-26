package com.sparta.trelloproject.domain.board.controller;

import com.sparta.trelloproject.domain.auth.entity.AuthUser;
import com.sparta.trelloproject.domain.board.dto.request.BoardRequest;
import com.sparta.trelloproject.domain.board.dto.response.BoardResponse;
import com.sparta.trelloproject.domain.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/workspaces/{workspaceId}/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    // 보드 생성
    @PostMapping
    public ResponseEntity<BoardResponse> createBoard(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable Long workspaceId,
            @RequestBody BoardRequest request) {

        BoardResponse response = boardService.createBoard(request, workspaceId, authUser.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 보드 수정
    @PatchMapping("/{boardId}")
    public ResponseEntity<BoardResponse> updateBoard(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable Long workspaceId,
            @PathVariable Long boardId,
            @RequestBody BoardRequest request) {

        BoardResponse response = boardService.updateBoard(boardId, request, authUser.getUserId());
        return ResponseEntity.ok(response);
    }

    // 보드 조회
    @GetMapping("/{boardId}")
    public ResponseEntity<BoardResponse> getBoardById(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable Long workspaceId,
            @PathVariable Long boardId) {

        BoardResponse response = boardService.getBoard(boardId, authUser.getUserId());
        return ResponseEntity.ok(response);
    }

    // 보드 삭제
    @DeleteMapping("/{boardId}")
    public ResponseEntity<BoardResponse.BoardDeleteResponse> deleteBoard(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable Long workspaceId,
            @PathVariable Long boardId) {

        BoardResponse.BoardDeleteResponse response = boardService.deleteBoard(boardId, authUser.getUserId());
        return ResponseEntity.ok(response);
    }
}
