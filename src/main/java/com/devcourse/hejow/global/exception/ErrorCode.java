package com.devcourse.hejow.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // 400
    LESS_THAN_MIN_AMOUNT(BAD_REQUEST, "최소 주문 금액을 만족하지 않습니다."),
    EMPTY_ORDER(BAD_REQUEST, "주문 내역이 존재하지 않습니다."),
    NOT_SUPPORT_MENU(BAD_REQUEST, "매장에 없는 메뉴입니다."),
    INVALID_REQUEST_INPUT(BAD_REQUEST, "잘못된 값을 입력 했습니다. 입력을 확인바랍니다."),

    // 404
    ORDER_NOT_FOUND(NOT_FOUND, "존재하지 않는 주문입니다."),
    SHOP_NOT_FOUND(NOT_FOUND, "존재하지 않는 매장입니다."),

    // 409
    ALREADY_CANCELED(CONFLICT, "이미 취소완료된 건입니다."),
    ALREADY_DELIVERY_STARTED(CONFLICT, "이미 배달 중입니다."),

    // 500
    INTERVAL_SERVER_ERROR(INTERNAL_SERVER_ERROR, "서버 내부 오류입니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
