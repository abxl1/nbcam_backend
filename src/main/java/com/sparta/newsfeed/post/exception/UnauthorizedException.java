package com.sparta.newsfeed.post.exception;

// 커스텀 예외 클래스: 인증 실패 시 발생
public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message); // 예외 발생 시 전달할 메시지를 설정
    }
}
