package com.devcourse.hejow.module.order.application;

import com.devcourse.hejow.global.exception.EntityNotFoundException;
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
    private static final int ZERO = 0;

    private final OrderRepository orderRepository;
    private final ShopService shopService;

    @Override
    public UUID create(UUID shopId, List<OrderItem> orderItems) {
        Shop shop = shopService.findById(shopId);
        int totalPrice = calculateTotalPrice(orderItems);
        shop.validateOrder(orderItems, totalPrice);

        return orderRepository.save(shop.getId(), orderItems, totalPrice);
    }

    @Override
    public void startDelivery(UUID id) {
        Order order = findById(id);
        order.validateDeliverable();
        orderRepository.startDelivery(id);
    }

    private Order findById(UUID id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 주문입니다."));
    }

    private int calculateTotalPrice(List<OrderItem> orderItems) {
        return orderItems.stream()
                .reduce(ZERO, (total, item) -> total + item.calculatePrice(), Integer::sum);
    }
}
