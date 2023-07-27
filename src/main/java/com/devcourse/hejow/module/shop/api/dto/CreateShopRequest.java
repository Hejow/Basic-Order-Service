package com.devcourse.hejow.module.shop.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

public record CreateShopRequest(@NotBlank(message = "이름은 필수로 입력해야 합니다.")
                                String name,
                                @PositiveOrZero(message = "최소 주문 금액은 양수여야 합니다.")
                                int minimumOrderPrice) {
}
