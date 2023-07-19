package com.devcourse.hejow.module.shop.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
class ShopRepositoryImpl implements ShopRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Optional<Shop> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public Optional<Shop> findByNameAndAddress(String name, String address) {
        return Optional.empty();
    }
}
