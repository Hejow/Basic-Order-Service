package com.devcourse.hejow.module.order.domain;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository {
    UUID save(UUID shopId, List<OrderItem> orderItems, int totalPrice);

    Optional<Order> findById(UUID id);
}
