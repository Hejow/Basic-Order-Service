package com.devcourse.hejow.global.exception.domain;

import com.devcourse.hejow.global.exception.CustomException;
import com.devcourse.hejow.global.exception.ErrorCode;

public class EntityNotFoundException extends CustomException {
    public EntityNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
