package com.devcourse.hejow.module.shop.domain;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record Menu(
        @NotBlank(message = "공백을 사용할 수 없습니다.") String name,
        @Min(value = 0, message = "음수를 사용할 수 없습니다.") int price
) {
}
