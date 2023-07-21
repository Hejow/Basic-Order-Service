package com.devcourse.hejow.module.shop.domain;

import com.devcourse.hejow.global.exception.common.ValidationFailException;
import com.devcourse.hejow.module.order.domain.OrderItem;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

import static com.devcourse.hejow.global.exception.ErrorCode.EMPTY_ORDER;
import static com.devcourse.hejow.global.exception.ErrorCode.LESS_THAN_MIN_AMOUNT;
import static com.devcourse.hejow.global.exception.ErrorCode.NOT_SUPPORT_MENU;

@Getter
public class Shop {
    private final UUID id;
    private final String name;
    private final List<Menu> menus;
    private final int minimumOrderPrice;

    public Shop(String name, List<Menu> menus, int minimumOrderPrice) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.menus = menus;
        this.minimumOrderPrice = minimumOrderPrice;
    }

    public void validateOrder(List<OrderItem> orderItems, int totalPrice) {
        validateMinOrderPrice(totalPrice);
        validateOrderItems(orderItems);
    }

    private void validateOrderItems(List<OrderItem> orderItems) {
        if (orderItems.isEmpty()) {
            throw new ValidationFailException(EMPTY_ORDER);
        }

        for (OrderItem orderItem : orderItems) {
            validateSupportMenu(orderItem.menu());
        }
    }

    private void validateSupportMenu(Menu menu) {
        if (!menus.contains(menu)) {
            throw new ValidationFailException(NOT_SUPPORT_MENU);
        }
    }

    private void validateMinOrderPrice(int totalPrice) {
        if (minimumOrderPrice > totalPrice) {
            throw new ValidationFailException(LESS_THAN_MIN_AMOUNT);
        }
    }
}
