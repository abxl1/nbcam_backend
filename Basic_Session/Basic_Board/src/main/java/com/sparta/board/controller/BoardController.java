package com.sparta.board.controller;
import com.sparta.board.dto.*;
import com.sparta.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/boards")
    public ResponseEntity< BoardSaveResponseDto > addBoard(@RequestBody BoardSaveRequestDto boardSaveRequestDto) {
        return ResponseEntity.ok(boardService.saveBoard(boardSaveRequestDto));
    }

    @GetMapping("/boards")
    public ResponseEntity< List<BoardSimpleResponseDto> > getBoards() {
        return ResponseEntity.ok(boardService.getBoards());
    }

    @GetMapping("/boards/{id}")
    public ResponseEntity< BoardDetailResponseDto > getBoard(@PathVariable Long id) {
        return ResponseEntity.ok(boardService.getBoard(id));
    }

    @PutMapping("/boards/{id}")
    public ResponseEntity< BoardTiltleUpdateResponseDto > updateBoard(Long id, @RequestBody BoardTiltleUpdateRequestDto boardTiltleUpdateRequestDto) {
        return ResponseEntity.ok(boardService.updateTitle(id, boardTiltleUpdateRequestDto));
    }

    @PutMapping("/boards/{id}")
    public ResponseEntity<BoardContentsUpdateResponseDto> updateBoard(Long id, @RequestBody BoardContentsUpdateRequestDto boardContentsUpdateRequestDto) {
        return ResponseEntity.ok(boardService.updateContents(id, boardContentsUpdateRequestDto));
    }

    @DeleteMapping("/boards/{id}")
    public void deleteBoard(Long id) {
        boardService.deleteBoard(id);
    }
}
