package com.sparta.trelloproject.domain.card.repository;

import com.sparta.trelloproject.domain.card.entity.CardAssignee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardAssigneeRepository extends JpaRepository<CardAssignee, Long> {
}
