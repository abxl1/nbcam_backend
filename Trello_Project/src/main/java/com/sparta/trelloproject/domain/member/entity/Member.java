package com.sparta.trelloproject.domain.member.entity;

import com.sparta.trelloproject.domain.member.enums.Assign;
import com.sparta.trelloproject.domain.user.entity.User;
import com.sparta.trelloproject.domain.workspace.entity.Workspace;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "members")
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING) // 사용자 역할을 문자열로 저장
    private Assign assign;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workspace_id", nullable = false)
    private Workspace workspace;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Member(User user, Workspace workspace) {
        this.user = user;
        this.assign = Assign.BOARD;
        this.workspace = workspace;
    }

    public Member(Member member, Workspace workspace) {
        this.assign = member.assign;
        this.workspace = workspace;
    }

    public void changeAssign(Assign newAssign) {
        this.assign = newAssign;
    }

    public void startAssign() {
        this.assign = Assign.MANAGER;
    }
}
