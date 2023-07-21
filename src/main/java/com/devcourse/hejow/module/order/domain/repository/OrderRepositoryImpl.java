package com.devcourse.hejow.module.order.domain.repository;

import com.devcourse.hejow.module.order.domain.Order;
import com.devcourse.hejow.module.order.domain.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
class OrderRepositoryImpl implements OrderRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<Order> findAllByShop(UUID shopId) {
        return null;
    }

    @Override
    public UUID save(UUID shopId, List<OrderItem> orderItems, int totalPrice) {
        return null;
    }

    @Override
    public Optional<Order> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public void startDelivery(UUID id) {

    }

    @Override
    public void cancelOrder(UUID id) {

    }
}
