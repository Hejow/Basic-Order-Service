package com.devcourse.hejow.module.order.api.dto;

import com.devcourse.hejow.module.order.domain.OrderItem;

import java.util.List;

public record OrderRequest(List<OrderItem> orderItems) {
}
