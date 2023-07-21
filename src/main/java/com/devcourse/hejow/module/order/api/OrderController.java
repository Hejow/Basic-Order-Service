package com.devcourse.hejow.module.order.api;

import com.devcourse.hejow.global.ApiResponse;
import com.devcourse.hejow.module.order.api.dto.OrderRequest;
import com.devcourse.hejow.module.order.application.OrderService;
import com.devcourse.hejow.module.order.application.dto.GetShopOrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @ResponseStatus(OK)
    @GetMapping("/{shopId}")
    public ApiResponse<List<GetShopOrderResponse>> getAllOrderByShop(@PathVariable UUID shopId) {
        List<GetShopOrderResponse> responses = orderService.getAllOrderByShop(shopId);
        return ApiResponse.ok(responses);
    }

    @ResponseStatus(CREATED)
    @PostMapping("/{shopId}")
    public ApiResponse<Void> createOrder(@PathVariable UUID shopId,
                                         @RequestBody OrderRequest request) {
        orderService.create(shopId, request.orderItems());
        return ApiResponse.noPayload();
    }

    @ResponseStatus(OK)
    @PatchMapping("/{id}/delivery")
    public ApiResponse<Void> startDelivery(@PathVariable UUID id) {
        orderService.startDelivery(id);
        return ApiResponse.noPayload();
    }

    @ResponseStatus(OK)
    @PatchMapping("/{id}/cancel")
    public ApiResponse<Void> cancelOrder(@PathVariable UUID id) {
        orderService.cancelOrder(id);
        return ApiResponse.noPayload();
    }
}
