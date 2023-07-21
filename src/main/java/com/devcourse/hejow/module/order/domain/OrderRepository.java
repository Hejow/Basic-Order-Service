package com.devcourse.hejow.module.order.domain;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository {
    List<Order> findAllByShop(UUID shopId);

    UUID save(UUID shopId, List<OrderItem> orderItems, int totalPrice);

    Optional<Order> findById(UUID id);

    void startDelivery(UUID id);

    void cancelOrder(UUID id);
}
