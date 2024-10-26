package org.example.expert.domain.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.manager.entity.Log;
import org.example.expert.domain.manager.repository.LogRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j(topic = "ManagerSaveLog")
@Aspect
@Component
@RequiredArgsConstructor
public class ManagerSaveLog {
    private final LogRepository logRepository;

    @Pointcut("execution(* org.example.expert.domain.manager.service.ManagerService.saveManager(..))")
    public void managerSaveLog() {}

    @After("managerSaveLog()")
    public void logAfter(JoinPoint joinPoint) {

        LocalDateTime apiRequestTime = LocalDateTime.now();
        Object[] args = joinPoint.getArgs();
        AuthUser authUser = (AuthUser) args[0];
        Long user = authUser.getUserId();

        Log newLog = new Log(user, apiRequestTime);
        logRepository.save(newLog);

        LocalDateTime logCreateTime = LocalDateTime.now();
        log.info("api 요청 시각 : {}, 로그 생성 시각 : {}, 요청한 유저의 id: {}", apiRequestTime, logCreateTime, user);
    }
}