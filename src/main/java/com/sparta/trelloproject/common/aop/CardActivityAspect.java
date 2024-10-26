package com.sparta.trelloproject.common.aop;

import com.sparta.trelloproject.common.exception.CustomException;
import com.sparta.trelloproject.common.exception.ErrorCode;
import com.sparta.trelloproject.domain.auth.entity.AuthUser;
import com.sparta.trelloproject.domain.card.entity.Card;
import com.sparta.trelloproject.domain.card.entity.CardActivity;
import com.sparta.trelloproject.domain.card.repository.CardActivityRepository;
import com.sparta.trelloproject.domain.card.repository.CardRepository;
import com.sparta.trelloproject.domain.card.service.CardActivityService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class CardActivityAspect {

    private final HttpServletRequest request;
    private final CardActivityService cardActivityService;
    private final CardActivityRepository cardActivityRepository;
    private final CardRepository cardRepository;

    @After("@annotation(CreateActivity)")
    public void createCardActivity(JoinPoint joinPoint) {

        AuthUser authUser = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Null 체크
        if (authUser == null) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        String userId = String.valueOf(authUser.getUserId());
        String activity = "유저ID " + userId + "이 ";
        String method = request.getMethod();

        if(method != null && method.equals("POST")){
            activity += "카드를 생성했습니다.";
            Card card = cardRepository.findTopByOrderByIdDesc();
            cardActivityService.createActivity(activity, card);
        }

        if (method != null && method.equals("PATCH")){
            activity += "카드를 수정했습니다.";
            Long cardId = (long) joinPoint.getArgs()[2];
            Card card = cardRepository.findById(cardId).orElseThrow();
            cardActivityService.createActivity(activity, card);
        }

    }
}
