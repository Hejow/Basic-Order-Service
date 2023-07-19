package com.devcourse.hejow.module.order.domain;

import com.devcourse.hejow.module.shop.domain.Shop;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

import static com.devcourse.hejow.module.order.domain.Order.Status.ORDERED;

@Getter
public class Order {
    public enum Status { ORDERED, DELIVERING, CANCELED }

    private final UUID id;
    private final Shop shop;
    private final List<OrderItem> orderItems;
    private final int totalPrice;
    private Status status;

    Order(UUID id, Shop shop, List<OrderItem> orderItems, Status status) {
        this.id = id;
        this.shop = shop;
        this.orderItems = orderItems;
        this.totalPrice = calculateTotalPrice(orderItems);
        this.status = status;
    }

    public static Order of(Shop shop, List<OrderItem> orderItems) {
        return new Order(UUID.randomUUID(), shop, orderItems, ORDERED);
    }

    private int calculateTotalPrice(List<OrderItem> orderItems) {
        return orderItems.stream()
                .reduce(0, (totalPrice, orderItem) ->
                        totalPrice + orderItem.orderCount() * orderItem.price(), Integer::sum);
    }
}
