package com.sparta.newsfeed.post.exception;

// 토큰이 누락된 경우 발생시키는 커스텀 예외 클래스
public class TokenMissingException extends RuntimeException {
    public TokenMissingException(String message) {
        super(message); // 예외 발생 시 전달할 메시지를 설정
    }
}
