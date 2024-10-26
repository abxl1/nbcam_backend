package com.sparta.trelloproject.domain.card.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "card_activity")
public class CardActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_activity_id", nullable = false)
    private Long id;

    private String activity;

    private LocalDateTime timestamp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id")
    private Card card;

    public CardActivity(String activity, Card card){
        this.activity = activity;
        this.timestamp = LocalDateTime.now();
        this.card = card;
    }

}
