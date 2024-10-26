package com.sparta.trelloproject.domain.card.repository;

import com.sparta.trelloproject.domain.card.entity.CardActivity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardActivityRepository extends JpaRepository<CardActivity, Long> {
}
