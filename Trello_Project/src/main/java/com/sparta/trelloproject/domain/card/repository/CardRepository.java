package com.sparta.trelloproject.domain.card.repository;

import com.sparta.trelloproject.domain.card.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long>, CustomCardRepository {
    Card findTopByOrderByIdDesc();

    // 제목으로 검색
    List<Card> findByTitleContainingAndTaskList_Id(String Title, Long taskListId);
}
