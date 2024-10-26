package org.example.expert.domain.todo.repository;

import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface TodoRepository extends JpaRepository<Todo, Long>, TodoQueryRepository {

    @Query("SELECT t FROM Todo t " +
            "LEFT JOIN FETCH t.user u " +
            "WHERE (t.weather IS NOT NULL OR t.weather = :weather) " +
            "AND (t.modifiedAt IS NOT NULL OR t.modifiedAt >= :startDate)" +
            "AND (t.modifiedAt IS NOT NULL OR t.modifiedAt <= :endDate)")
    Page<Todo> findTodoByWeatherAndModifiedAt(@Param("weather") String weather,
                                 @Param("startDate") LocalDateTime startDate,
                                 @Param("endDate") LocalDateTime endDate,
                                 Pageable pageable);
}
