package com.sparta.trelloproject.domain.member.controller;

import com.sparta.trelloproject.domain.auth.entity.AuthUser;
import com.sparta.trelloproject.domain.member.dto.request.MemberRequest;
import com.sparta.trelloproject.domain.member.dto.request.MemberSaveRequest;
import com.sparta.trelloproject.domain.member.dto.response.MemberResponse;
import com.sparta.trelloproject.domain.member.dto.response.MemberSaveResponse;
import com.sparta.trelloproject.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/workspaces/{workspaceId}")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/members")
    public ResponseEntity<MemberSaveResponse> saveMember(
            @AuthenticationPrincipal AuthUser authUser,
            @RequestBody MemberSaveRequest request,
            @PathVariable Long workspaceId
    ) {
        MemberSaveResponse response = memberService.saveMember(authUser, request, workspaceId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/members/{memberId}")
    public ResponseEntity<MemberResponse> updateMember(
            @AuthenticationPrincipal AuthUser authUser,
            @RequestBody MemberRequest request,
            @PathVariable Long workspaceId,
            @PathVariable Long memberId
    ) {
        return ResponseEntity.ok(memberService.updateMember(authUser, request, workspaceId, memberId));
    }
}
