package com.sparta.trelloproject.domain.workspace.service;

import com.sparta.trelloproject.common.exception.CustomException;
import com.sparta.trelloproject.common.exception.ErrorCode;
import com.sparta.trelloproject.domain.auth.entity.AuthUser;
import com.sparta.trelloproject.domain.member.entity.Member;
import com.sparta.trelloproject.domain.member.repository.MemberRepository;
import com.sparta.trelloproject.domain.user.dto.request.UserCreateRequest;
import com.sparta.trelloproject.domain.user.dto.request.UserGetRequest;
import com.sparta.trelloproject.domain.user.entity.User;
import com.sparta.trelloproject.domain.user.enums.UserRole;
import com.sparta.trelloproject.domain.user.repository.UserRepository;
import com.sparta.trelloproject.domain.workspace.dto.response.WorkspaceResponse;
import com.sparta.trelloproject.domain.workspace.entity.Workspace;
import com.sparta.trelloproject.domain.workspace.repository.WorkspaceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WorkspaceServiceTest {

  @InjectMocks
  private WorkspaceService workspaceService;

  @Mock
  private UserRepository userRepository;

  @Mock
  private WorkspaceRepository workspaceRepository;

  @Mock
  private MemberRepository memberRepository;

  private AuthUser authUser;
  private UserCreateRequest userCreateRequest;
  private UserGetRequest userGetRequest;
  private User adminUser;
  private User normalUser;
  private Member member;
  private Workspace workspace;

  @BeforeEach
  public void setUp() {
    // AuthUser 생성
    authUser = new AuthUser(1L, "124@naver.com", UserRole.ROLE_ADMIN);
    normalUser = new User("123@naver.com", "12345!#(21", UserRole.ROLE_USER);
    // Request 생성
    userCreateRequest = new UserCreateRequest("제목", "설명");
    userGetRequest = new UserGetRequest(1L);
    // ADMIN 권한을 가진 유저 생성
    adminUser = new User("124@naver.com", "12#232", UserRole.ROLE_ADMIN);

    workspace = new Workspace();
    member = new Member();
  }
  @Test
  public void 워크스페이스_생성성공() {
    // when
    when(userRepository.findById(authUser.getUserId())).thenReturn(Optional.of(adminUser));
    // 메서드 호출
    WorkspaceResponse response = workspaceService.createWorkspace(authUser, userCreateRequest);

    // then, null이 아닌지 확인
    assertNotNull(response);
    // 제목, 설명 체크
    assertEquals("제목", response.getTitle());
    assertEquals("설명", response.getExplaination());
    verify(workspaceRepository, times(1)).save(any(Workspace.class));
    verify(memberRepository, times(1)).save(any(Member.class));
  }
  @Test
  public void 워크스페이스_생성실패() {
    // 비어 있는 일반 유저 생성
    when(userRepository.findById(authUser.getUserId())).thenReturn(Optional.of(normalUser));

    // 예외 발생 검증
    CustomException exception = assertThrows(CustomException.class, () -> {
      workspaceService.createWorkspace(authUser, userCreateRequest);
    });

    assertEquals(ErrorCode.PERMISSION_ERROR, exception.getErrorCode());
  }

  @Test
  public void 워크스페이스_조회성공() {
    when(userRepository.findById(authUser.getUserId())).thenReturn(Optional.of(adminUser));
    when(workspaceRepository.findById(userGetRequest.getWorkspaceId())).thenReturn(Optional.of(workspace));
    when(memberRepository.findByUserId(authUser.getUserId())).thenReturn(Optional.of(member));
    // when
    WorkspaceResponse response = workspaceService.getWorkspace(authUser, userGetRequest);

    // then
    assertNotNull(response);
    assertEquals(userGetRequest.getWorkspaceId(), response.getId());
    assertEquals(workspace.getTitle(), response.getTitle());
    assertEquals(workspace.getExplaination(), response.getExplaination());
    verify(userRepository, times(1)).save(any(User.class));
    verify(workspaceRepository, times(1)).save(any(Workspace.class));
  }
}
