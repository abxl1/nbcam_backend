package com.sparta.schedulerjpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Schedule extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;
    private String scheduleUserName;
    private String scheduleTitle;
    private String scheduleContents;


    // 영속성 전이 삭제
    @OneToMany(mappedBy = "schedule", cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();

    public Schedule(String scheduleUserName, String scheduleTitle, String scheduleContents) {
        this.scheduleUserName = scheduleUserName;
        this.scheduleTitle = scheduleTitle;
        this.scheduleContents = scheduleContents;
    }

    // 일정 업데이트 메서드
    public void scheduleUpdate(String scheduleUserName, String scheduleTitle, String scheduleContents) {
        this.scheduleUserName = scheduleUserName;
        this.scheduleTitle = scheduleTitle;
        this.scheduleContents = scheduleContents;
    }
}
