package com.sparta.trelloproject.domain.member.dto.response;

import com.sparta.trelloproject.domain.member.entity.Member;
import lombok.Getter;

@Getter
public class MemberResponse {
    private final Long id;
    private final String assign;
    private final String msg;

    public MemberResponse(Member member) {
        this.id = member.getId();
        this.assign = member.getAssign().name();
        this.msg = "멤버 권한 변경이 성공적으로 완료되었습니다.";
    }
}