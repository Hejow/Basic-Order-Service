package com.devcourse.hejow.global.exception.domain;

import com.devcourse.hejow.global.exception.CustomException;
import com.devcourse.hejow.global.exception.ErrorCode;

public class ValidationFailException extends CustomException {
    public ValidationFailException(ErrorCode errorCode) {
        super(errorCode);
    }
}
