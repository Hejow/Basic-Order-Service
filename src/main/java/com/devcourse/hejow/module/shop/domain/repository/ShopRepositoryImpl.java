package com.devcourse.hejow.module.shop.domain.repository;

import com.devcourse.hejow.module.shop.domain.Menu;
import com.devcourse.hejow.module.shop.domain.Shop;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
class ShopRepositoryImpl implements ShopRepository {
    private final RowMapper<Shop> shopMapper = (resultSet, i) -> {
        UUID shopId = UUID.fromString(resultSet.getString("shop_id"));
        String name = resultSet.getString("name");
        int minOrderPrice = resultSet.getInt("min_order_price");
        List<Menu> menus = findAllMenu(shopId);
        return new Shop(shopId, name, menus, minOrderPrice);
    };

    private final RowMapper<Menu> menuMapper = (resultSet, i) -> {
        String name = resultSet.getString("name");
        int price = resultSet.getInt("price");
        return new Menu(name, price);
    };

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public UUID save(String name, int minimumOrderPrice) {
        String sql = "INSERT INTO shops(shop_id, name, min_order_price) VALUES(:shopId, :name, :minimumOrderPrice)";
        UUID shopId = UUID.randomUUID();

        jdbcTemplate.update(sql, toShopParameter(shopId, name, minimumOrderPrice));
        return shopId;
    }

    @Override
    public void saveMenu(UUID id, Menu menu) {

    }

    @Override
    public Optional<Shop> findById(UUID id) {
        String sql = "SELECT * FROM shops WHERE shop_id = :shopId";

        return jdbcTemplate.query(sql, Collections.singletonMap("shopId", id.toString()), shopMapper).stream()
                .findFirst();
    }

    private Map<String, Object> toShopParameter(UUID shopId, String name, int minimumOrderPrice) {
        return new HashMap<>() {{
            put("shopId", shopId.toString());
            put("name", name);
            put("minimumOrderPrice", minimumOrderPrice);
        }};
    }

    private List<Menu> findAllMenu(UUID shopId) {
        String sql = "SELECT * FROM menus WHERE shop_id = :shopId";
        return jdbcTemplate.query(sql, Collections.singletonMap("shopId", shopId.toString()), menuMapper);
    }
}
