package com.devcourse.hejow.module.order.domain;

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
    public UUID save(UUID shopId, List<OrderItem> orderItems, int totalPrice) {
        return null;
    }

    @Override
    public Optional<Order> findById(UUID id) {
        return Optional.empty();
    }
}
