package com.devcourse.hejow.module.order.domain;

import com.devcourse.hejow.module.shop.domain.Shop;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

import static com.devcourse.hejow.module.order.domain.Order.Status.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
    public enum Status { ORDERED, DELIVERING, CANCELED }

    private final UUID id;
    private final Shop shop;
    private final List<OrderItem> orderItems;
    private final int totalPrice;
    private Status status;

    public void validateDeliverable() {
        if (this.status != ORDERED) {
            throw new IllegalStateException("이미 주문이 완료되었거나 취소된 건입니다.");
        }
    }
}
