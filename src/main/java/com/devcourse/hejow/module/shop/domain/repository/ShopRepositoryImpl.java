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
    private static final String INSERT_SHOP_SQL = "INSERT INTO shops(shop_id, name, min_order_price) VALUES(:shopId, :name, :minimumOrderPrice)";
    private static final String INSERT_MENU_SQL = "INSERT INTO menus(shop_id, name, price) VALUES(:shopId, :name, :price)";
    private static final String SELECT_BY_SHOP_ID_SQL = "SELECT * FROM shops WHERE shop_id = :shopId";
    private static final String SELECT_ALL_MENU_BY_SHOP_ID_SQL = "SELECT * FROM menus WHERE shop_id = :shopId";

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
        UUID shopId = UUID.randomUUID();
        jdbcTemplate.update(INSERT_SHOP_SQL, toShopParameterMap(shopId, name, minimumOrderPrice));
        return shopId;
    }
    @Override
    public void saveMenu(UUID id, Menu menu) {
        jdbcTemplate.update(INSERT_MENU_SQL, toMenuParameterMap(id, menu));
    }

    @Override
    public Optional<Shop> findById(UUID id) {
        return jdbcTemplate.query(SELECT_BY_SHOP_ID_SQL, Collections.singletonMap("shopId", id.toString()), shopMapper)
                .stream()
                .findFirst();
    }

    private List<Menu> findAllMenu(UUID shopId) {
        return jdbcTemplate.query(SELECT_ALL_MENU_BY_SHOP_ID_SQL, Collections.singletonMap("shopId", shopId.toString()), menuMapper);
    }

    private Map<String, Object> toShopParameterMap(UUID shopId, String name, int minimumOrderPrice) {
        return new HashMap<>() {{
            put("shopId", shopId.toString());
            put("name", name);
            put("minimumOrderPrice", minimumOrderPrice);
        }};
    }

    private Map<String, Object> toMenuParameterMap(UUID shopId, Menu menu) {
        return new HashMap<>() {{
            put("shopId", shopId.toString());
            put("name", menu.name());
            put("price", menu.price());
        }};
    }
}
