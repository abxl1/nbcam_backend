package org.example.expert.domain.todo.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.todo.dto.response.TodoProjectionsDto;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static org.example.expert.domain.comment.entity.QComment.comment;
import static org.example.expert.domain.manager.entity.QManager.manager;
import static org.example.expert.domain.todo.entity.QTodo.todo;
import static org.example.expert.domain.user.entity.QUser.user;

@Repository
@RequiredArgsConstructor
public class TodoQueryRepositoryImpl implements TodoQueryRepository {

    private final JPAQueryFactory q;

    @Override
    public Todo findByIdByDsl(long todoId) {
        return q
                .select(todo)
                .from(todo)
                .join(todo.user).fetchJoin()
                .where(todoIdEq(todoId))
                .fetchOne();
    }

    private BooleanExpression todoIdEq(Long todoId) {
        return todoId != null ? todo.id.eq(todoId) : null;
    }

    public Page<TodoProjectionsDto> findByTitleAndCreatedAtAndNickname(
            String title,
            LocalDateTime startDate,
            LocalDateTime endDate,
            String nickname,
            Pageable pageable) {

        List<TodoProjectionsDto> content = q
                .select(Projections.constructor(
                                TodoProjectionsDto.class,
                                todo.title,
                                manager.countDistinct(),
                                comment.countDistinct()
                        )
                )
                .from(todo)
                .leftJoin(todo.managers, manager)
                .leftJoin(manager.user, user)
                .leftJoin(todo.comments, comment)
                .where(
                        titleContain(title),
                        createAtBetween(startDate, endDate),
                        nicknameContain(nickname)
                )
                .groupBy(todo.id)
                .orderBy(todo.createdAt.desc())
                .fetch();

        Long totalCount = q
                .select(todo.count())
                .from(todo)
                .where(
                        titleContain(title),
                        createAtBetween(startDate, endDate),
                        nicknameContain(nickname)
                )
                .fetchOne();


        return new PageImpl<>(content, pageable, totalCount);
    }

    private BooleanExpression titleContain(String title) {
        return title != null ? todo.title.contains(title) : null;
    }

    private Predicate nicknameContain(String nickname) {
        return nickname != null ? user.nickname.contains(nickname) : null;
    }

    private BooleanExpression createAtBetween(LocalDateTime statrtDate, LocalDateTime endDate) {

        BooleanExpression condition = null;

        if (statrtDate != null && endDate != null) {
            condition = todo.createdAt.between(statrtDate, endDate);
        } else if (statrtDate != null) {
            condition = todo.createdAt.goe(statrtDate);
        } else if (endDate != null) {
            condition = todo.createdAt.loe(endDate);
        }
        return condition;
    }
}
