package com.devcourse.hejow.module.shop.api;

import com.devcourse.hejow.global.ApiResponse;
import com.devcourse.hejow.module.shop.api.dto.CreateMenuRequest;
import com.devcourse.hejow.module.shop.api.dto.CreateShopRequest;
import com.devcourse.hejow.module.shop.application.ShopService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/shops")
@RequiredArgsConstructor
public class ShopController {
    private final ShopService shopService;

    @ResponseStatus(CREATED)
    @PostMapping
    public ApiResponse<UUID> create(@RequestBody @Valid CreateShopRequest request) {
        UUID id = shopService.create(request.name(), request.minimumOrderPrice());
        return ApiResponse.withPayload(id);
    }

    @ResponseStatus(CREATED)
    @PostMapping("/{id}/menus")
    public ApiResponse<Void> createMenu(@PathVariable UUID id,
                                        @RequestBody @Valid CreateMenuRequest request) {
        shopService.newMenu(id, request.name(), request.price());
        return ApiResponse.noPayload();
    }
}
