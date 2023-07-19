package com.devcourse.hejow.module.shop.application;

import com.devcourse.hejow.global.exception.EntityNotFoundException;
import com.devcourse.hejow.module.shop.domain.Shop;
import com.devcourse.hejow.module.shop.domain.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class ShopServiceImpl implements ShopService {
    private final ShopRepository shopRepository;

    @Override
    public Shop findByNameAndAddress(String name, String address) {
        return shopRepository.findByNameAndAddress(name, address)
                .orElseThrow(() -> new EntityNotFoundException("Incorrect Shop"));
    }
}
