package com.devcourse.hejow.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException {
    private final ErrorCode errorCode;

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    HttpStatus getHttpStatus() {
        return errorCode.getHttpStatus();
    }
}
