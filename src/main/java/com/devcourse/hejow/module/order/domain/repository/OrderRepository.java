package com.devcourse.hejow.module.order.domain.repository;

import com.devcourse.hejow.module.order.domain.Order;
import com.devcourse.hejow.module.order.domain.OrderItem;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository {
    List<Order> findAllOrderByShopId(UUID shopId);

    UUID save(UUID shopId, List<OrderItem> orderItems, int totalPrice);

    Optional<Order> findById(UUID id);

    void startDelivery(UUID id);

    void cancelOrder(UUID id);
}
