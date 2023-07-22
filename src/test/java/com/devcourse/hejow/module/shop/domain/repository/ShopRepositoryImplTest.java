package com.devcourse.hejow.module.shop.domain.repository;

import com.devcourse.hejow.module.shop.domain.Shop;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@ContextConfiguration(classes = ShopRepositoryImpl.class)
class ShopRepositoryImplTest {
    @Autowired
    private ShopRepository shopRepository;

    @Test
    @DisplayName("매장을 생성할 때 입력한 값이 그대로 저장되어야 한다.")
    void saveTest() {
        // given
        String name = "hejow-chicken";
        int minOrderPrice = 15_000;

        // when
        UUID shopId = shopRepository.save(name, minOrderPrice);

        // then
        Optional<Shop> shopOptional = shopRepository.findById(shopId);
        assertThat(shopOptional).isNotEmpty();

        Shop shop = shopOptional.get();
        assertThat(shop.getId()).isEqualTo(shopId);
        assertThat(shop.getName()).isEqualTo(name);
        assertThat(shop.getMinimumOrderPrice()).isEqualTo(minOrderPrice);
    }
}
