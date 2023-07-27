package com.devcourse.hejow.module.shop.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record CreateMenuRequest(@NotBlank(message = "이름은 필수로 입력해야 합니다.")
                                String name,
                                @Min(value = 500, message = "가격은 500원 이상이어야 합니다.")
                                int price) {
}
