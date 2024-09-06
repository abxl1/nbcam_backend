package com.sparta.board.service;

import com.sparta.board.dto.*;
import com.sparta.board.entity.Board;
import com.sparta.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    // post
    @Transactional
    public BoardSaveResponseDto saveBoard(BoardSaveRequestDto boardSaveRequestDto) {
        Board board = new Board(boardSaveRequestDto.getTitle(), boardSaveRequestDto.getContents());
        Board savedBoard = boardRepository.save(board);
        return new BoardSaveResponseDto(savedBoard.getTitle(), savedBoard.getContents());
    }

    // get all
    public List<BoardSimpleResponseDto> getBoards() {
        List<Board> boards = boardRepository.findAll();
        List<BoardSimpleResponseDto> BoardSimpleResponseDtos = new ArrayList<>();
        for (Board board : boards) {
            BoardSimpleResponseDtos.add(new BoardSimpleResponseDto(board.getTitle()));
        }
        return BoardSimpleResponseDtos;
    }

    // get one
    public BoardDetailResponseDto getBoard(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new NullPointerException("게시글이 없습니다."));
        return new BoardDetailResponseDto(board.getTitle(), board.getContents());

    }

    // update title
    @Transactional
    public BoardTiltleUpdateResponseDto updateTitle(Long id, BoardTiltleUpdateRequestDto boardTiltleUpdateRequestDto) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new NullPointerException("게시글이 없습니다."));
        board.titleUpdate(boardTiltleUpdateRequestDto.getTitle());
        return new BoardTiltleUpdateResponseDto(board.getId(), board.getTitle());
    }

    // update contents
    @Transactional
    public BoardContentsUpdateResponseDto updateContents(Long id, BoardContentsUpdateRequestDto boardTiltleUpdateRequestDto) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new NullPointerException("게시글이 없습니다."));
        board.titleUpdate(boardTiltleUpdateRequestDto.getContents());
        return new BoardContentsUpdateResponseDto(board.getId(), board.getContents());
    }

    // delete
    @Transactional
    public void deleteBoard(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new NullPointerException("게시글이 없습니다."));
        boardRepository.delete(board);
    }
}