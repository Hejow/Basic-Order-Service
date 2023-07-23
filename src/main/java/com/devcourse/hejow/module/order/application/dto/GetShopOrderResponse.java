package com.devcourse.hejow.module.order.application.dto;

import com.devcourse.hejow.module.order.domain.Order;

import java.util.UUID;

public record GetShopOrderResponse(UUID id, int totalPrice, Order.Status status) {
}
