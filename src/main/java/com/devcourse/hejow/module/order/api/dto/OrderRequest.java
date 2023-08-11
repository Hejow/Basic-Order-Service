package com.devcourse.hejow.module.order.api.dto;

import com.devcourse.hejow.module.order.domain.OrderItem;
import jakarta.validation.Valid;

import java.util.List;

public record OrderRequest(
        @Valid List<OrderItem> orderItems
) {
}
