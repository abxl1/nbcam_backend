package com.sparta.trelloproject.domain.list.service;

import com.sparta.trelloproject.common.exception.CustomException;
import com.sparta.trelloproject.common.exception.ErrorCode;
import com.sparta.trelloproject.domain.auth.entity.AuthUser;
import com.sparta.trelloproject.domain.board.entity.Board;
import com.sparta.trelloproject.domain.board.repository.BoardRepository;
import com.sparta.trelloproject.domain.list.dto.request.TaskListRequest;
import com.sparta.trelloproject.domain.list.dto.request.TaskListSaveRequest;
import com.sparta.trelloproject.domain.list.dto.response.TaskListResponse;
import com.sparta.trelloproject.domain.list.dto.response.TaskListSaveResponse;
import com.sparta.trelloproject.domain.list.entity.TaskList;
import com.sparta.trelloproject.domain.list.repository.TaskListRepository;
import com.sparta.trelloproject.domain.notification.service.NotificationService;
import com.sparta.trelloproject.domain.user.entity.User;
import com.sparta.trelloproject.domain.user.enums.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.sparta.trelloproject.domain.user.entity.User.fromAuthUser;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskListService {

    private final TaskListRepository taskListRepository;
    private final BoardRepository boardRepository;
    private final NotificationService notificationService;

    private Board getBoard(Long boardId) {
        return boardRepository.findById(boardId)
            .orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND));
    }

    // 조회 루틴
    private User validateList(AuthUser authUser) {
        User user = fromAuthUser(authUser);

        if (user.getUserRole() == UserRole.ROLE_USER) {
            throw new CustomException(ErrorCode.ROLE_ERROR, "일반 유저는 접근할 수 없습니다.");
        }
        return user;
    }

    /**
     * 리스트 조회
     *
     * @param authUser 인증된 사용자
     * @param boardId  생성할 리스트의 대상 보드
     * @return HTTPStatus.created
     */
    @Transactional
    public TaskListSaveResponse saveList(
        AuthUser authUser,
        TaskListSaveRequest request,
        Long boardId
    ) {
        User user = validateList(authUser);
        Board board = getBoard(boardId);

        Long index = taskListRepository.countByBoardId(boardId);

        TaskList taskList = new TaskList(request, board, index);
        TaskList savedTaskList = taskListRepository.save(taskList);

        // 리스트 생성 알림 전송
        notificationService.sendListCreationNotification(user.getEmail(), boardId.toString(), savedTaskList.getId().toString(), savedTaskList.getTitle());
        return new TaskListSaveResponse(savedTaskList);
    }

    /**
     * 리스트 조회
     *
     * @param authUser 인증된 사용자
     * @param boardId  조회할 리스트의 대상 보드
     * @param listId   조회할 리스트의 아이디
     * @return HTTPStatus.ok
     */
    public TaskListResponse getList(
        AuthUser authUser,
        Long boardId,
        Long listId
    ) {
        validateList(authUser);
        Board board = getBoard(boardId);

        TaskList list = taskListRepository.findById(listId).orElseThrow(
            () -> new CustomException(ErrorCode.LIST_NOT_FOUND, "해당 리스트를 찾을 수 없습니다."));

        return new TaskListResponse(
            list.getId(),
            list.getTitle(),
            list.getIndex()
        );
    }

    /**
     * 리스트 변경
     *
     * @param authUser 인증된 사용자
     * @param request  title, index
     * @param boardId  변경할 리스트의 대상 보드
     * @param listId   변경할 리스트의 아이디
     * @return HTTPStatus.ok
     */
    @Transactional
    public TaskListResponse updateList(
        AuthUser authUser,
        TaskListRequest request,
        Long boardId,
        Long listId
    ) {

        validateList(authUser);
        Board board = getBoard(boardId);

        Long totalListIndex = (long) board.getLists().size();

        TaskList list = taskListRepository.findById(listId).orElseThrow(
            () -> new CustomException(ErrorCode.LIST_NOT_FOUND, "해당 리스트를 찾을 수 없습니다."));

        list.updateList(request);

        if (request.getIndex() != null) {
            list.changeListIndex(board, list, boardId, request.getIndex(), totalListIndex);
        }

        return null;
    }

    /**
     * 리스트 변경
     *
     * @param authUser 인증된 사용자
     * @param boardId  삭제할 리스트의 대상 보드
     * @param listId   삭제할 리스트의 아이디
     */
    @Transactional
    public void deleteList(
        AuthUser authUser,
        Long boardId,
        Long listId
    ) {
        validateList(authUser);
        Board board = getBoard(boardId);

        TaskList list = taskListRepository.findById(listId).orElseThrow(
            () -> new CustomException(ErrorCode.LIST_NOT_FOUND, "해당 리스트를 찾을 수 없습니다."));

        taskListRepository.delete(list);
    }
}