package com.sparta.trelloproject.domain.board.dto.response;

import com.sparta.trelloproject.domain.board.entity.Board;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class BoardResponse {
    private String message; // 응답메세지
    private Long boardId;
    private String title;
    private String background;
    private String createdAt;
    private String modifiedAt;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // 보드 조회 시 응답
    public BoardResponse(Board board) {
        this.boardId = board.getId();
        this.title = board.getTitle();
        this.background = board.getBackground();
        this.createdAt = board.getCreatedAt().format(formatter);
        this.modifiedAt = board.getModifiedAt().format(formatter);
        this.message = "보드 조회가 완료되었습니다.";
    }

    // 보드 생성 및 수정 시 응답
    public BoardResponse(Board board, String action) {
        this.boardId = board.getId();
        this.title = board.getTitle();
        this.background = board.getBackground();
        this.createdAt = board.getCreatedAt().format(formatter);
        this.modifiedAt = board.getModifiedAt().format(formatter);
        this.message = action.equals("create") ? "보드가 생성되었습니다." : "보드가 수정되었습니다.";
    }

    // 보드 삭제 시 응답
    public static class BoardDeleteResponse {
        private String message;
        private Long boardId;
        private String deletedAt;

        public BoardDeleteResponse(Long boardId) {
            this.boardId = boardId;
            this.message = "보드가 삭제되었습니다";
            this.deletedAt = LocalDateTime.now().format(formatter);
        }

        public String getMessage() {
            return message;
        }

        public Long getBoardId() {
            return boardId;
        }

        public String getDeletedAt() {
            return deletedAt;
        }
    }
}
