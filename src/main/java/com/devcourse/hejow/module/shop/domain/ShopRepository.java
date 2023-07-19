package com.devcourse.hejow.module.shop.domain;

import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ShopRepository {
    Optional<Shop> findById(UUID id);

    Optional<Shop> findByNameAndAddress(String name, String address);
}
