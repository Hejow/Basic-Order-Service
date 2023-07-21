package com.devcourse.hejow.module.order.domain;

import com.devcourse.hejow.module.shop.domain.Shop;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
    public enum Status { ORDERED, DELIVERING, CANCELED }

    private final UUID id;
    private final Shop shop;
    private final List<OrderItem> orderItems;
    private final int totalPrice;
    private Status status;
}
