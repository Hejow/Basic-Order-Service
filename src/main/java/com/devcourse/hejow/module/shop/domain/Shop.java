package com.devcourse.hejow.module.shop.domain;

import com.devcourse.hejow.module.order.domain.OrderItem;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Shop {
    private final UUID id;
    private final String name;
    private final List<Menu> menus;
    private final int minimumOrderPrice;

    public void validateOrder(List<OrderItem> orderItems, int totalPrice) {
        validateMinOrderPrice(totalPrice);
        validateOrderItems(orderItems);
    }

    private void validateOrderItems(List<OrderItem> orderItems) {
        if (orderItems.isEmpty()) {
            throw new IllegalArgumentException("주문 내역이 존재하지 않습니다.");
        }

        for (OrderItem orderItem : orderItems) {
            validateSupportMenu(orderItem.menu());
        }
    }

    private void validateSupportMenu(Menu menu) {
        if (!menus.contains(menu)) {
            throw new IllegalArgumentException("매장에 없는 메뉴입니다.");
        }
    }

    private void validateMinOrderPrice(int totalPrice) {
        if (minimumOrderPrice > totalPrice) {
            throw new IllegalArgumentException("최소 주문 금액을 만족하지 않습니다.");
        }
    }
}
