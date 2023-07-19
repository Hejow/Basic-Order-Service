package com.devcourse.hejow.module.shop.application;

import com.devcourse.hejow.module.shop.domain.Shop;
import org.springframework.stereotype.Service;

@Service
public interface ShopService {
    Shop findByNameAndAddress(String name, String address);
}
