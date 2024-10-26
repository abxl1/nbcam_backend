package com.sparta.trelloproject.common.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private ErrorCode errorCode;

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public CustomException(ErrorCode errorCode, String detail) {
        super(String.format(errorCode.getMessage(), detail)); // 메시지 포맷팅
        this.errorCode = errorCode;
    }
}
