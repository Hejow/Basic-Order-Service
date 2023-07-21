package com.devcourse.hejow.module.shop.application;

import com.devcourse.hejow.global.exception.common.EntityNotFoundException;
import com.devcourse.hejow.module.shop.domain.Menu;
import com.devcourse.hejow.module.shop.domain.Shop;
import com.devcourse.hejow.module.shop.domain.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.devcourse.hejow.global.exception.ErrorCode.SHOP_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ShopService {
    private final ShopRepository shopRepository;

    public UUID create(String name, int minimumOrderPrice) {
        return shopRepository.save(name, minimumOrderPrice);
    }

    public void newMenu(UUID id, String name, int price) {
        Menu menu = new Menu(name, price);
        shopRepository.saveMenu(id, menu);
    }

    public Shop findById(UUID id) {
        return shopRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(SHOP_NOT_FOUND));
    }
}
