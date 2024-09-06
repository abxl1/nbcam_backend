package com.sparta.newsfeed.post.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice // 모든 컨트롤러에서 발생하는 예외를 처리하기 위한 설정
public class GlobalExceptionHandler {

    @ExceptionHandler(UnauthorizedException.class) // UnauthorizedException 발생 시 처리
    @ResponseStatus(HttpStatus.UNAUTHORIZED) // 401 Unauthorized 상태 코드를 반환
    public ResponseEntity<String> handleUnauthorizedException(UnauthorizedException ex) {
        // 예외 메시지와 함께 401 상태를 응답으로 반환
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(TokenMissingException.class) // TokenMissingException 발생 시 처리
    @ResponseStatus(HttpStatus.BAD_REQUEST) // 400 Bad Request 상태 코드를 반환
    public ResponseEntity<String> handleTokenMissingException(TokenMissingException ex) {
        // 예외 메시지와 함께 400 상태를 응답으로 반환
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthorizedCheckException.class) //AuthorizedCheckException 발생 시 처리
    @ResponseStatus(HttpStatus.FORBIDDEN) // 403 Forbidden 상태 코드를 반환
    public ResponseEntity<String> handleAuthorizedCheckException(AuthorizedCheckException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
    }
}
