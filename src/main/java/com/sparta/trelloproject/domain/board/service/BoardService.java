package com.sparta.trelloproject.domain.board.service;

import com.sparta.trelloproject.common.exception.CustomException;
import com.sparta.trelloproject.common.exception.ErrorCode;
import com.sparta.trelloproject.domain.board.dto.request.BoardRequest;
import com.sparta.trelloproject.domain.board.dto.response.BoardResponse;
import com.sparta.trelloproject.domain.board.entity.Board;
import com.sparta.trelloproject.domain.board.repository.BoardRepository;
import com.sparta.trelloproject.domain.board.dto.request.BoardRequest;
import com.sparta.trelloproject.domain.board.dto.response.BoardResponse;
import com.sparta.trelloproject.domain.notification.service.NotificationService;
import com.sparta.trelloproject.domain.user.entity.User;
import com.sparta.trelloproject.domain.user.enums.UserRole;
import com.sparta.trelloproject.domain.user.repository.UserRepository;
import com.sparta.trelloproject.domain.workspace.entity.Workspace;
import com.sparta.trelloproject.domain.workspace.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final WorkspaceRepository workspaceRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;


    // 유저 찾기
    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    // 워크스페이스 찾기
    private Workspace getWorkspace(Long workspaceId) {
        return workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new CustomException(ErrorCode.WORKSPACE_NOT_FOUND));
    }

    // 보드 찾기
    private Board getBoardById(Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND));
    }

    // 유저 권한 검증
    private void validateUserRole(User user) {
        if(user.getUserRole() == UserRole.ROLE_USER) {
            throw new CustomException(ErrorCode.BOARD_FORBIDDEN);
        }
    }

    // 보드 제목 검증
    private void validateBoardTitle(String title) {
        if(title == null || title.isEmpty()) {
            throw new CustomException(ErrorCode.BOARD_TITLE_EMPTY);
        }
    }

    // 보드 생성
    @Transactional
    public BoardResponse createBoard(BoardRequest request, Long workspaceId, Long userId) {
        User user = getUser(userId);

        validateBoardTitle(request.getTitle());
        Workspace workspace = getWorkspace(workspaceId);
        validateUserRole(user);

        Board board = new Board(workspaceId, userId, request.getTitle(), request.getBackground());
        boardRepository.save(board);

        // 보드 생성 알림 전송
        notificationService.sendBoardCreationNotification(user.getEmail(), workspaceId.toString(), board.getId().toString(), board.getTitle());


        return new BoardResponse(board, "create");
    }

    // 보드 수정
    @Transactional
    public BoardResponse updateBoard(Long boardId, BoardRequest request, Long userId) {
        Board board = getBoardById(boardId);

        if(!board.getUserId().equals(userId)) {
            throw new CustomException(ErrorCode.BOARD_FORBIDDEN);
        }

        validateBoardTitle(request.getTitle());
        User user = getUser(userId);
        validateUserRole(user);

        board.update(request.getTitle(), request.getBackground());
        boardRepository.save(board);

        return new BoardResponse(board, "update");
    }

    // 보드 조회
    @Transactional(readOnly = true)
    public BoardResponse getBoard(Long boardId, Long userId) {
        Board board = getBoardById(boardId);

        if(!board.getUserId().equals(userId)) {
            throw new CustomException(ErrorCode.BOARD_FORBIDDEN);
        }
        return new BoardResponse(board);
    }

    // 보드 삭제 (연관된 리스트와 카드 삭제)
    @Transactional
    public BoardResponse.BoardDeleteResponse deleteBoard(Long boardId, Long userId) {
        Board board = getBoardById(boardId);

        if(!board.getUserId().equals(userId)) {
            throw new CustomException(ErrorCode.BOARD_FORBIDDEN);
        }

        User user = getUser(userId);
        validateUserRole(user);

        boardRepository.delete(board); // 연관된 리스트와 카드도 자동 삭제됨

        return new BoardResponse.BoardDeleteResponse(boardId);
    }
}
