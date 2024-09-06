package com.sparta.basicspringsession.controller;

import com.sparta.basicspringsession.dto.*;
import com.sparta.basicspringsession.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/members")
    public ResponseEntity<MemberSaveResponseDto> addMember(@RequestBody MemberSaveRequestDto memberSaveRequestDto) {
        return ResponseEntity.ok(memberService.saveMember(memberSaveRequestDto));
    }

    @GetMapping("/members")
    public ResponseEntity<List<MemberSimpleResponseDto>> getMembers() {
        return ResponseEntity.ok(memberService.getMembers());
    }

    @GetMapping("/members/{id}")
    public ResponseEntity<MemberDetailResponseDto> getMember(@PathVariable Long id) {
        return ResponseEntity.ok(memberService.getMember(id));
    }

    @PutMapping("/members/{id}")
    public ResponseEntity<MemberUpdateResponseDto> updateMember(@PathVariable Long id, @RequestBody MemberUpdateRequestDto memberUpdateRequestDto) {
        return ResponseEntity.ok(memberService.updateMember(id, memberUpdateRequestDto));
    }


    @DeleteMapping("/members/{id}")
    public void deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
    }
}
