package com.devcourse.hejow.module.order.application;

import com.devcourse.hejow.global.exception.EntityNotFoundException;
import com.devcourse.hejow.module.order.application.dto.GetShopOrderResponse;
import com.devcourse.hejow.module.order.domain.Order;
import com.devcourse.hejow.module.order.domain.OrderItem;
import com.devcourse.hejow.module.order.domain.OrderRepository;
import com.devcourse.hejow.module.shop.application.ShopService;
import com.devcourse.hejow.module.shop.domain.Shop;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private static final int ZERO = 0;

    private final OrderRepository orderRepository;
    private final ShopService shopService;

    public List<GetShopOrderResponse> getAllOrderByShop(UUID shopId) {
        return orderRepository.findAllByShop(shopId).stream()
                .map(this::toResponse)
                .toList();
    }

    public UUID create(UUID shopId, List<OrderItem> orderItems) {
        Shop shop = shopService.findById(shopId);
        int totalPrice = calculateTotalPrice(orderItems);
        shop.validateOrder(orderItems, totalPrice);

        return orderRepository.save(shop.getId(), orderItems, totalPrice);
    }

    public void startDelivery(UUID id) {
        Order order = findById(id);
        order.validateDeliverable();
        orderRepository.startDelivery(id);
    }

    public void cancelOrder(UUID id) {
        Order order = findById(id);
        order.validateCancelable();
        orderRepository.cancelOrder(id);
    }

    private Order findById(UUID id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 주문입니다."));
    }

    private int calculateTotalPrice(List<OrderItem> orderItems) {
        return orderItems.stream()
                .reduce(ZERO, (total, item) -> total + item.calculatePrice(), Integer::sum);
    }

    private GetShopOrderResponse toResponse(Order order) {
        return new GetShopOrderResponse(order.getId(), order.getTotalPrice());
    }
}
