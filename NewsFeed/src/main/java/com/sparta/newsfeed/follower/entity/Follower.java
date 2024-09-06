package com.sparta.newsfeed.follower.entity;

import com.sparta.newsfeed.profile.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor
public class Follower { // 어떤 유저가 어떤 유저를 팔로우했는지 출력하는 테이블

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 테이블 자체의 데이터 아이디

    @Column(name = "email")
    private String email; // 유저 이메일

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user; // 유저 아이디, PK

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower_id")
    private User follower; // 팔로우 대상 유저 아이디

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = null; // 친구 신청 상태

    public enum Status { // 친구신청을 보냈을 때 상태
        pending,
        accepted
    }

    public Follower(User user, User follower) {
        this.user = user;
        this.follower = follower;
        this.status = Status.pending;
    }

    // 친구 신청 수락 시 세팅
    public void changeAcceptStatus() {
        if (this.status == Status.pending) {
            this.status = Status.accepted;
        }
    }

}
