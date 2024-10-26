package org.example.expert.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;

@Slf4j(topic = "TestAop")
@Aspect
@Component
@RequiredArgsConstructor
public class TestAop {

    @Pointcut("execution(* org.example.expert.domain.user.controller.UserAdminController.*(..))")
    public void changeUserRole() {}

    @Pointcut("execution(* org.example.expert.domain.comment.controller.CommentAdminController.*(..))")
    public void deleteComment() {}


    @After("changeUserRole() || deleteComment()")
    public void afterUseApiLog() {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        long requestUserId = (long) request.getAttribute("userId");
        LocalDateTime requestTime = LocalDateTime.now();
        String requestURL = request.getRequestURL().toString();

        log.info("요청한 사용자의 id : {}, API 요청 시각 : {}, API 요청 URL : {}", requestUserId, requestTime, requestURL);
    }
}
