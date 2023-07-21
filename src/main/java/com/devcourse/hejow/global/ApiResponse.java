package com.devcourse.hejow.global;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@JsonPropertyOrder({"message", "payload"})
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponse<T> {
    private static final String SUCCESS = "성공적으로 요청을 수행했습니다.";

    private final String message;
    private final T payload;

    public static <T> ApiResponse<T> ok(T payload) {
        return new ApiResponse<>(SUCCESS, payload);
    }

    public static ApiResponse<Void> noPayload() {
        return new ApiResponse<>(SUCCESS, null);
    }
}
