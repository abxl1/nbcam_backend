package com.sparta.trelloproject.domain.list.repository;

import com.sparta.trelloproject.domain.list.entity.TaskList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskListRepository extends JpaRepository<TaskList, Long> {
    Long countByBoardId(Long boardId);

    Optional<TaskList> findById(Long listId);
}
