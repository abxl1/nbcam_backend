package org.example.expert.domain.todo.repository;

import org.example.expert.domain.todo.dto.response.TodoProjectionsDto;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface TodoQueryRepository {
    Todo findByIdByDsl(long todoId);

    Page<TodoProjectionsDto> findByTitleAndCreatedAtAndNickname(String title, LocalDateTime startTime, LocalDateTime endTime, String nickname, Pageable pageable);
}
