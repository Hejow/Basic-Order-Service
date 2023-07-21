package com.devcourse.hejow.module.shop.application;

import com.devcourse.hejow.module.shop.domain.Shop;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface ShopService {
    UUID create(String name, int minimumOrderPrice);

    void newMenu(UUID id, String name, int price);

    Shop findById(UUID id);
}
