package com.devcourse.hejow.module.order.application.dto;

import java.util.UUID;

public record GetShopOrderResponse(UUID id, int totalPrice) {
}
