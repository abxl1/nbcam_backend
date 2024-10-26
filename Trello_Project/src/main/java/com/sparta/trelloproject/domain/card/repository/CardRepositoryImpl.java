package com.sparta.trelloproject.domain.card.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.trelloproject.domain.card.dto.response.CardActivityResponse;
import com.sparta.trelloproject.domain.card.dto.response.CardDetailResponse;
import com.sparta.trelloproject.domain.card.entity.Card;
import com.sparta.trelloproject.domain.card.entity.QCard;
import com.sparta.trelloproject.domain.card.entity.QCardActivity;
import com.sparta.trelloproject.domain.comment.dto.response.CommentResponse;
import com.sparta.trelloproject.domain.comment.entity.QComment;
import com.sparta.trelloproject.domain.user.entity.QUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.sparta.trelloproject.domain.card.entity.QCard.card;

@RequiredArgsConstructor
public class CardRepositoryImpl implements CustomCardRepository {

    @Autowired
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public CardDetailResponse findByCardDetail(Long cardId) {

        QCard qCard = card;
        QCardActivity qActivity = QCardActivity.cardActivity;
        QComment qComment = QComment.comment;
        QUser qUser = QUser.user;

        List<Tuple> results = jpaQueryFactory
                .selectDistinct(qCard,
                        qCard.title,
                        qCard.description,
                        qActivity.activity,
                        qActivity.timestamp)
                .from(qCard)
                .join(qCard.cardActivities, qActivity).fetchJoin()
                .where(qCard.id.eq(cardId))
                .fetch();

        List<CardActivityResponse> activityResponses = results.stream()
                .map(tuple -> new CardActivityResponse(tuple.get(qActivity.activity),
                        tuple.get(qActivity.timestamp)
                ))
                .collect(Collectors.toList());

        List<Tuple> commentList = jpaQueryFactory
                .select(qComment, qUser.email)
                .from(qComment)
                .join(qComment.user, qUser).fetchJoin()
                .where(qComment.card.id.eq(cardId))
                .fetch();

        List<CommentResponse> commentResponses = commentList.stream()
                .map(tuple -> new CommentResponse(tuple.get(qComment).getCommentId(),
                        tuple.get(qComment).getText(),
                        tuple.get(qComment.user.email)
                ))
                .collect(Collectors.toList());

        return new CardDetailResponse(results.get(0).get(qCard.title),
                results.get(0).get(qCard.description),
                activityResponses,
                commentResponses);
    }


    @Override
    public Page<Card> findByCondition(String title, String description, LocalDateTime deadline, Long assignId, Pageable pageable) {

        BooleanExpression condition = buildCondition(title, description, deadline, assignId, pageable);

        List<Card> result = jpaQueryFactory
                .selectFrom(card)
                .where(condition)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = jpaQueryFactory
                .selectFrom(card)
                .where(condition)
                .fetch().size();

        return new PageImpl<>(result, pageable, total);

    }


    private BooleanExpression buildCondition(String title, String description, LocalDateTime deadline, Long assignId, Pageable pageable){
        BooleanExpression condition = card.isNotNull();

        if(title != null && title.isEmpty()){
            condition = condition.and(card.title.containsIgnoreCase(title));
        }

        if(description != null && description.isEmpty()){
            condition = condition.and(card.description.containsIgnoreCase(description));
        }

        if(deadline != null){
            condition = condition.and(card.deadline.before(deadline));
        }

        if(assignId != null){
            condition = condition.and(card.cardAssignees.any().user.id.eq(assignId));
        }

        return condition;

    }

}
