package com.devcourse.hejow.module.order.domain;

import com.devcourse.hejow.global.exception.common.ValidationFailException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

import static com.devcourse.hejow.global.exception.ErrorCode.ALREADY_CANCELED;
import static com.devcourse.hejow.global.exception.ErrorCode.ALREADY_DELIVERY_STARTED;
import static com.devcourse.hejow.global.exception.ErrorCode.NOT_ABLE_TO_CANCEL;
import static com.devcourse.hejow.module.order.domain.Order.Status.CANCELED;
import static com.devcourse.hejow.module.order.domain.Order.Status.DELIVERING;
import static com.devcourse.hejow.module.order.domain.Order.Status.ORDERED;

@Getter
@AllArgsConstructor
public class Order {
    public enum Status { ORDERED, DELIVERING, CANCELED }

    private final UUID id;
    private final UUID shopId;
    private final List<OrderItem> orderItems;
    private final int totalPrice;
    private Status status;

    public void validateDeliverable() {
        if (this.status != ORDERED) {
            throw new ValidationFailException(NOT_ABLE_TO_CANCEL);
        }
    }

    public void validateCancelable() {
        if (this.status == CANCELED) {
            throw new ValidationFailException(ALREADY_CANCELED);
        }

        if (this.status == DELIVERING) {
            throw new ValidationFailException(ALREADY_DELIVERY_STARTED);
        }
    }
}
