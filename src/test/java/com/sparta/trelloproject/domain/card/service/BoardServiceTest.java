package com.sparta.trelloproject.domain.card.service;

import com.sparta.trelloproject.common.exception.CustomException;
import com.sparta.trelloproject.common.exception.ErrorCode;
import com.sparta.trelloproject.domain.board.dto.request.BoardRequest;
import com.sparta.trelloproject.domain.board.dto.response.BoardResponse;
import com.sparta.trelloproject.domain.board.entity.Board;
import com.sparta.trelloproject.domain.board.repository.BoardRepository;
import com.sparta.trelloproject.domain.board.service.BoardService;
import com.sparta.trelloproject.domain.user.entity.User;
import com.sparta.trelloproject.domain.user.enums.UserRole;
import com.sparta.trelloproject.domain.user.repository.UserRepository;
import com.sparta.trelloproject.domain.workspace.entity.Workspace;
import com.sparta.trelloproject.domain.workspace.repository.WorkspaceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BoardServiceTest {

    @Mock
    private BoardRepository boardRepository;
    @Mock
    private WorkspaceRepository workspaceRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private BoardService boardService;

    @Test
    public void 보드생성성공() {
        // Given
        Long workspaceId = 1L;
        Long userId = 1L;
        String userEmail = "email22@naver.com";
        UserRole userRole = UserRole.ROLE_ADMIN;

        // User 객체 생성
        User user = new User(userEmail, "password123A!", userRole);
        // BoardRequest 객체 생성
        BoardRequest request = new BoardRequest("보드생성할거야", "FFB2F5");
        // Workspace 객체 생성
        Workspace workspace = new Workspace("워크스페이스 생성", "워크스페이스 내용 부분", user);

        // Mock 설정
        when(workspaceRepository.findById(workspaceId)).thenReturn(Optional.of(workspace));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // When
        BoardResponse response = boardService.createBoard(request, workspaceId, userId);

        // Then
        assertNotNull(response);
        assertEquals("보드생성할거야", response.getTitle());
        verify(boardRepository, times(1)).save(any(Board.class));
    }

    @Test
    public void 보드수정성공() {
        // Given
        Long boardId = 1L;
        Long userId = 1L;
        // User 객체 생성
        User user = new User("email22@naver.com", "password123A!", UserRole.ROLE_ADMIN);
        // Board 객체 생성
        BoardRequest updateRequest = new BoardRequest("보드수정해달라우", "FFF2F5");

        // 기존 Board 객체 생성
        Board savedBoard = new Board(boardId, userId, "보드생성할거야", "FFB2F5");

        when(boardRepository.findById(boardId)).thenReturn(Optional.of(savedBoard));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        when(boardRepository.save(any(Board.class))).thenAnswer(invocationOnMock -> {
            Board updatedBoard = invocationOnMock.getArgument(0);
            return updatedBoard;
        });

        // When
        BoardResponse response = boardService.updateBoard(boardId, updateRequest, userId);

        // Then
        assertNotNull(response);
        assertEquals("보드수정해달라우", response.getTitle());
        assertEquals("FFF2F5", response.getBackground());
        verify(boardRepository, times(1)).save(any(Board.class));
    }

    @Test
    public void 보드생성실_워크스페이스없음() {
        //Given
        Long workspaceId = 1L;
        Long userId = 1L;
        BoardRequest request = new BoardRequest("워크스페이스없어서생성실패", "FFB2F5");

        User user = new User("email22@naver.com", "password123A!", UserRole.ROLE_ADMIN);

        // Mock 설정: 워크스페이스 없음
        when(workspaceRepository.findById(workspaceId)).thenReturn(Optional.empty());

        // When & Then
        CustomException exception = assertThrows(CustomException.class, () -> {
            boardService.createBoard(request, workspaceId, userId);
        });
        assertEquals(ErrorCode.WORKSPACE_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    public void 보드수정실패_권한없음() {
        // Given
        Long boardId = 1L;
        Long userId = 1L;
        User user = new User("email22@naver.com", "password123A!", UserRole.ROLE_USER);
        BoardRequest updateRequest = new BoardRequest("보드수정해달라우", "FFF2F5");

        // 기존 Board 객체 생성
        Board savedBoard = new Board(boardId, userId, "보드생성할거야", "FFB2F5");

        when(boardRepository.findById(boardId)).thenReturn(Optional.of(savedBoard));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // When & Then
        CustomException exception = assertThrows(CustomException.class, () -> {
            boardService.updateBoard(boardId, updateRequest, userId);
        });
        assertEquals(ErrorCode.BOARD_FORBIDDEN, exception.getErrorCode());
    }

    @Test
    public void 보드조회성공() {
        // Given
        Long boardId = 1L;
        Long userId = 1L;
        Board board = new Board(1L, userId, "보드조회성공할거야", "FFF2F5");
        when(boardRepository.findById(boardId)).thenReturn(Optional.of(board));

        // When
        BoardResponse response = boardService.getBoard(boardId, userId);

        assertNotNull(response);
        assertEquals("보드조회성공할거야", response.getTitle());
    }

    @Test
    public void 보드조회실패_권한없음() {
        // Given
        Long boardId = 1L;
        Long userId = 1L;
        Long otherUserId = 2L;
        Board board = new Board(1L, otherUserId, "보드조회가실패했어요", "FFF2F5");
        when(boardRepository.findById(boardId)).thenReturn(Optional.of(board));

        // When & Then
        CustomException exception = assertThrows(CustomException.class, () -> {
            boardService.getBoard(boardId, userId);
        });

        assertEquals(ErrorCode.BOARD_FORBIDDEN, exception.getErrorCode());
    }

    @Test
    public void 보드조회실패_보드없음() {
        // Given
        Long boardId = 1L;
        Long userId = 1L;
        when(boardRepository.findById(boardId)).thenReturn(Optional.empty());

        // When & Then
        CustomException exception = assertThrows(CustomException.class, () -> {
            boardService.getBoard(boardId, userId);
        });

        assertEquals(ErrorCode.BOARD_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    public void 보드삭제성공() {
        // Given
        Long boardId = 1L;
        Long userId = 1L;
        String email = "email22@naver.com";
        String password = "password123A!";

        // User 객체 생성 (email, password, userRole을 사용)
        User user = new User(email, password, UserRole.ROLE_ADMIN);

        Board board = new Board(1L, userId, "보드가삭제됐다우", "FFF2F5");
        when(boardRepository.findById(boardId)).thenReturn(Optional.of(board));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // When
        BoardResponse.BoardDeleteResponse response = boardService.deleteBoard(boardId, userId);

        // Then
        assertNotNull(response);
        assertEquals(boardId, response.getBoardId());
        verify(boardRepository, times(1)).delete(board);
    }

    @Test
    public void 보드삭제실패_권한없음() {
        // Given
        Long boardId = 1L;
        Long userId = 1L;
        Long otherUserId = 2L;
        Board board = new Board(1L, otherUserId, "보드삭제할수는없음", "FFF2F5");
        when(boardRepository.findById(boardId)).thenReturn(Optional.of(board));

        // When & Then
        CustomException exception = assertThrows(CustomException.class, () -> {
            boardService.deleteBoard(boardId, userId);
        });

        assertEquals(ErrorCode.BOARD_FORBIDDEN, exception.getErrorCode());
        verify(boardRepository, times(0)).delete(board);
    }

    @Test
    public void 보드삭제실패_보드없음() {
        // Given
        Long boardId = 1L;
        Long userId = 1L;
        when(boardRepository.findById(boardId)).thenReturn(Optional.empty());

        // When & Then
        CustomException exception = assertThrows(CustomException.class, () -> {
            boardService.deleteBoard(boardId, userId);
        });

        assertEquals(ErrorCode.BOARD_NOT_FOUND, exception.getErrorCode());
        verify(boardRepository, times(0)).delete(any());
    }
}
