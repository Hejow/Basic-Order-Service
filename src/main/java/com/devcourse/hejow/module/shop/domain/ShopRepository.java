package com.devcourse.hejow.module.shop.domain;

import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ShopRepository {
    UUID save(String name, int minimumOrderPrice);

    void saveMenu(UUID id, Menu menu);

    Optional<Shop> findById(UUID id);
}
