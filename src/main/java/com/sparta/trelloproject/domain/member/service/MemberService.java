package com.sparta.trelloproject.domain.member.service;

import com.sparta.trelloproject.common.exception.CustomException;
import com.sparta.trelloproject.common.exception.ErrorCode;
import com.sparta.trelloproject.domain.auth.entity.AuthUser;
import com.sparta.trelloproject.domain.member.dto.request.MemberRequest;
import com.sparta.trelloproject.domain.member.dto.request.MemberSaveRequest;
import com.sparta.trelloproject.domain.member.dto.response.MemberResponse;
import com.sparta.trelloproject.domain.member.dto.response.MemberSaveResponse;
import com.sparta.trelloproject.domain.member.entity.Member;
import com.sparta.trelloproject.domain.member.enums.Assign;
import com.sparta.trelloproject.domain.member.repository.MemberRepository;
import com.sparta.trelloproject.domain.user.entity.User;
import com.sparta.trelloproject.domain.user.repository.UserRepository;
import com.sparta.trelloproject.domain.workspace.entity.Workspace;
import com.sparta.trelloproject.domain.workspace.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final UserRepository userRepository;
    private final WorkspaceRepository workspaceRepository;

    /**
     * 멤버 추가하기
     * @param authUser 인증된 사용자
     * @param request email
     * @param workspaceId 추가할 멤버의 대상 워크스페이스
     * @return HTTPStatus.created
     */
    @Transactional
    public MemberSaveResponse saveMember(
            AuthUser authUser,
            MemberSaveRequest request,
            Long workspaceId
    ) {

        User user = User.fromAuthUser(authUser);

        User newUser = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND, "추가할 사용자를 찾을 수 없습니다.")
        );

        Workspace workspace = workspaceRepository.findById(workspaceId).orElseThrow(
                () -> new CustomException(ErrorCode.WORKSPACE_NOT_FOUND)
        );

        if (newUser.getEmail().equals(authUser.getEmail())) {
            throw new CustomException(ErrorCode.SELF_REQUEST_FORBIDDEN, "본인을 초대할 수 없습니다.");
        }

        if (memberRepository.existsByUserId(newUser.getId())) {
            throw new CustomException(ErrorCode.USER_ALREADY_EXISTS, "이미 멤버가 존재합니다.");
        }

        Member member = memberRepository.save(new Member(newUser, workspace));
        return new MemberSaveResponse(member);
    }

    /**
     * 멤버 역할 변경하기
     * @param authUser 인증된 사용자
     * @param request email, assign
     * @param workspaceId 변경할 멤버의 대상 워크스페이스
     * @param memberId 변결할 멤버의 아이디
     * @return HTTPStatus.ok
     */
    @Transactional
    public MemberResponse updateMember(
            AuthUser authUser,
            MemberRequest request,
            Long workspaceId,
            Long memberId
    ) {

        User user = User.fromAuthUser(authUser);

        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND, "변경할 사용자를 찾을 수 없습니다.")
        );

        Workspace workspace = workspaceRepository.findById(workspaceId).orElseThrow(
                () -> new CustomException(ErrorCode.WORKSPACE_NOT_FOUND)
        );

        if (member.getAssign().name().equals(request.getAssign())) {
            throw new CustomException(ErrorCode.SAME_ROLE_REQUEST, "같은 권한으로 변경할 수 없습니다.");
        }

        if (!request.getAssign().equals(Assign.BOARD.name()) &&
                !request.getAssign().equals(Assign.MANAGER.name()) &&
                !request.getAssign().equals(Assign.READ_ONLY.name())
        ) {
            throw new CustomException(ErrorCode.ROLE_NOT_FOUND, "존재하지 않는 권한입니다.");
        }

        Assign newAssign = Assign.valueOf(request.getAssign());
        if (member.getAssign() != Assign.MANAGER) {
            member.changeAssign(newAssign);
            memberRepository.save(member);
        }

        return new MemberResponse(member);
    }
}
