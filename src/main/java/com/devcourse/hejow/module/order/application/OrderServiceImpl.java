package com.devcourse.hejow.module.order.application;

import com.devcourse.hejow.module.order.domain.Order;
import com.devcourse.hejow.module.order.domain.OrderItem;
import com.devcourse.hejow.module.order.domain.OrderRepository;
import com.devcourse.hejow.module.shop.application.ShopService;
import com.devcourse.hejow.module.shop.domain.Shop;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ShopService shopService;

    @Override
    public UUID create(String name, String address, List<OrderItem> orderItems) {
        Shop shop = shopService.findByNameAndAddress(name, address);
        Order order = Order.of(shop, orderItems);
        orderRepository.save(order);
        return order.getId();
    }
}
