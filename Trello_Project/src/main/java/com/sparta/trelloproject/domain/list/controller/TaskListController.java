package com.sparta.trelloproject.domain.list.controller;

import com.sparta.trelloproject.domain.auth.entity.AuthUser;
import com.sparta.trelloproject.domain.list.dto.request.TaskListRequest;
import com.sparta.trelloproject.domain.list.dto.request.TaskListSaveRequest;
import com.sparta.trelloproject.domain.list.dto.response.TaskListResponse;
import com.sparta.trelloproject.domain.list.dto.response.TaskListSaveResponse;
import com.sparta.trelloproject.domain.list.service.TaskListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/boards/{boardId}")
@RequiredArgsConstructor
public class TaskListController {

    private final TaskListService taskListService;

    @PostMapping("/lists")
    public ResponseEntity<TaskListSaveResponse> saveList(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable Long boardId,
            @RequestBody TaskListSaveRequest request
    ) {
        TaskListSaveResponse response = taskListService.saveList(authUser, request, boardId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/lists/{listId}")
    public ResponseEntity<TaskListResponse> getList(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable("boardId") Long boardId,
            @PathVariable Long listId
    ) {
        return ResponseEntity.ok(taskListService.getList(authUser, boardId, listId));
    }

    @PatchMapping("/lists/{listId}")
    public ResponseEntity<TaskListResponse> updateList(
            @AuthenticationPrincipal AuthUser authUser,
            @RequestBody TaskListRequest request,
            @PathVariable("boardId") Long boardId,
            @PathVariable Long listId
    ) {
        return ResponseEntity.ok(taskListService.updateList(authUser, request, boardId, listId));
    }

    @DeleteMapping("/lists/{listId}")
    public ResponseEntity<Void> deleteList(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable("boardId") Long boardId,
            @PathVariable Long listId
    ) {
        taskListService.deleteList(authUser, boardId, listId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
