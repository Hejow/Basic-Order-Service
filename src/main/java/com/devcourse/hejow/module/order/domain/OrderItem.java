package com.devcourse.hejow.module.order.domain;

import com.devcourse.hejow.module.shop.domain.Menu;

public record OrderItem(Menu menu, int orderCount) {
    public int calculatePrice() {
        return menu.price() * orderCount;
    }
}
