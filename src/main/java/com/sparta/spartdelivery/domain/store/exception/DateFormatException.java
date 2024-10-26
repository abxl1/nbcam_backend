package com.sparta.spartdelivery.domain.store.exception;

import com.sparta.spartdelivery.common.exception.CommonException;
import org.springframework.http.HttpStatus;

public class DateFormatException extends CommonException {

    public DateFormatException(HttpStatus status, String message) {
        super(status, message);
    }
}
