package com.devcourse.hejow.module.order.application;

import com.devcourse.hejow.module.order.domain.OrderItem;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface OrderService {
    UUID create(String name, String address, List<OrderItem> orderItems);
}
