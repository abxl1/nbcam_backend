package com.sparta.trelloproject.domain.workspace.controller;

import com.sparta.trelloproject.domain.auth.entity.AuthUser;
import com.sparta.trelloproject.domain.user.dto.request.UserCreateRequest;
import com.sparta.trelloproject.domain.user.dto.request.UserGetRequest;
import com.sparta.trelloproject.domain.workspace.dto.response.WorkspaceResponse;
import com.sparta.trelloproject.domain.workspace.service.WorkspaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/workspaces")
public class WorkspaceController {

  private final WorkspaceService workspaceService;

  // 워크스페이스 조회
  @GetMapping
  public ResponseEntity<WorkspaceResponse> getWorkspace(@AuthenticationPrincipal AuthUser authUser,
                                                        @RequestBody UserGetRequest userGetRequest) {
    WorkspaceResponse response = workspaceService.getWorkspace(authUser, userGetRequest);

    return ResponseEntity.ok(response);
  }

  // 워크스페이스 생성
  @PostMapping
  public ResponseEntity<WorkspaceResponse> createWorkspace(@AuthenticationPrincipal AuthUser authUser,
                                                           @RequestBody UserCreateRequest userCreateRequest) {

    WorkspaceResponse response = workspaceService.createWorkspace(authUser, userCreateRequest);

    return ResponseEntity.ok(response);
  }

  // 워크스페이스 수정
  @PatchMapping
  public ResponseEntity<WorkspaceResponse> updateWorkspace(@AuthenticationPrincipal AuthUser authUser,
                                                           @RequestBody com.sparta.trelloproject.domain.user.request.UserUpdateRequest userUpdateRequest) {

    WorkspaceResponse response = workspaceService.updateWorkspace(authUser, userUpdateRequest);

    return ResponseEntity.ok(response);
  }

  // 워크스페이스 삭제
  @DeleteMapping("/{workspaceId}")
  public ResponseEntity<String> deleteWorkspace(@AuthenticationPrincipal AuthUser authUser,
                                                           @PathVariable Long workspaceId) {

    workspaceService.deleteWorkspace(authUser, workspaceId);

    return ResponseEntity.ok("워크스페이스가 삭제되었습니다.");
  }


}
