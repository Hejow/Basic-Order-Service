package com.devcourse.hejow.module.order.domain;

import com.devcourse.hejow.module.shop.domain.Shop;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
public class Order {
    private final UUID id;
    private final Shop shop;
    private final List<OrderItem> orderItems;
    private final int totalPrice;

    public Order(UUID id, Shop shop, List<OrderItem> orderItems, int totalPrice) {
        this.id = id;
        this.shop = shop;
        this.orderItems = orderItems;
        this.totalPrice = totalPrice;
    }
}
