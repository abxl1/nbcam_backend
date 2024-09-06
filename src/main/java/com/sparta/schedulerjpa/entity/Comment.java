package com.sparta.schedulerjpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends Timestamped {

    public String getCommentContents;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;
    private String commentUserName;
    private String commentContents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    public Comment (String commentUserName, String commentContents, Schedule schedule) {
        this.commentUserName = commentUserName;
        this.commentContents = commentContents;
        this.schedule = schedule;
    }

    // 댓글 업데이트 메서드
    public void commentUpdate(String commentUserName, String commentContents) {
        this.commentUserName = commentUserName;
        this.commentContents = commentContents;
    }
}
