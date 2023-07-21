package com.devcourse.hejow.module.shop.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateShopRequest(@NotBlank String name,
                                @NotNull int minimumOrderPrice) {
}
