package com.sparta.trelloproject.domain.card.repository;

import com.sparta.trelloproject.domain.card.dto.response.CardDetailResponse;
import com.sparta.trelloproject.domain.card.entity.Card;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface CustomCardRepository {
    CardDetailResponse findByCardDetail(Long cardId);
    Page<Card> findByCondition(String title, String description, LocalDateTime deadline, Long assignId, Pageable pageable);
}
