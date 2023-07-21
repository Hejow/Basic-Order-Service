package com.devcourse.hejow.module.shop.domain.repository;

import com.devcourse.hejow.module.shop.domain.Menu;
import com.devcourse.hejow.module.shop.domain.Shop;
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
    public UUID save(String name, int minimumOrderPrice) {
        return null;
    }

    @Override
    public void saveMenu(UUID id, Menu menu) {

    }

    @Override
    public Optional<Shop> findById(UUID id) {
        return Optional.empty();
    }
}
