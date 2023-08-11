package com.devcourse.hejow.module.order.domain;

import com.devcourse.hejow.module.shop.domain.Menu;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

public record OrderItem(
        @Valid Menu menu,
        @Min(value = 1, message = "최소 하나는 주문해야 합니다.") int orderCount
) {
    public int calculatePrice() {
        return menu.price() * orderCount;
    }
}
