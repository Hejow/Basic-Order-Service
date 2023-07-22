package com.devcourse.hejow.module.order.domain.repository;

import com.devcourse.hejow.module.order.domain.Order;
import com.devcourse.hejow.module.order.domain.OrderItem;
import com.devcourse.hejow.module.shop.domain.Menu;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static com.devcourse.hejow.module.order.domain.Order.Status.ORDERED;
import static com.devcourse.hejow.module.order.domain.Order.Status.valueOf;

@Component
@Primary
@RequiredArgsConstructor
class OrderRepositoryImpl implements OrderRepository {
    private final RowMapper<Order> orderMapper = (resultSet, i) -> {
        UUID orderId = UUID.fromString(resultSet.getString("order_id"));
        UUID shopId = resultSet.getString("shop_id") == null ? null : UUID.fromString(resultSet.getString("shop_id")); // todo : erase null
        int totalPrice = resultSet.getInt("total_price");
        Order.Status status = valueOf(resultSet.getString("status"));
        List<OrderItem> orderItems = findAllOrderItem(orderId);
        return new Order(orderId, shopId, orderItems, totalPrice, status);
    };

    private final RowMapper<OrderItem> orderItemMapper = (resultSet, i) -> {
        String name = resultSet.getString("name");
        int price = resultSet.getInt("price");
        int count = resultSet.getInt("count");
        return new OrderItem(new Menu(name, price), count);
    };

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<Order> findAllByShop(UUID shopId) {
        return null;
    }

    @Override
    public UUID save(UUID shopId, List<OrderItem> orderItems, int totalPrice) {
        String sql = "INSERT INTO orders(order_id, shop_id, total_price, status) VALUES(:orderId, :shopId, :totalPrice, :status)";

        UUID orderId = UUID.randomUUID();
        jdbcTemplate.update(sql, toOrderParameterMap(orderId, shopId, totalPrice));
        saveAllOrderItem(orderId, orderItems);

        return orderId;
    }

    @Override
    public Optional<Order> findById(UUID id) {
        String sql = "SELECT * FROM orders WHERE order_id = :orderId";

        return jdbcTemplate.query(sql, Collections.singletonMap("orderId", id.toString()), orderMapper)
                .stream()
                .findFirst();
    }

    @Override
    public void startDelivery(UUID id) {

    }

    @Override
    public void cancelOrder(UUID id) {

    }

    public List<OrderItem> findAllOrderItem(UUID id) {
        String sql = "SELECT * FROM order_items WHERE order_id = :orderId";
        return jdbcTemplate.query(sql, Collections.singletonMap("orderId", id.toString()), orderItemMapper);
    }

    private Map<String, Object> toOrderParameterMap(UUID orderId, UUID shopId, int totalPrice) {
        return new HashMap<>() {{
            put("orderId", orderId.toString());
            put("shopId", shopId == null ? null : shopId.toString()); // todo : erase null
            put("totalPrice", totalPrice);
            put("status", ORDERED.name());
        }};
    }

    private void saveAllOrderItem(UUID orderId, List<OrderItem> orderItems) {
        String sql = "INSERT INTO order_items(order_id, name, count, price) VALUES(:orderId, :name, :orderCount, :price)";
        jdbcTemplate.batchUpdate(sql, toOrderItemBatchParameterSource(orderId, orderItems));
    }

    private SqlParameterSource[] toOrderItemBatchParameterSource(UUID orderId, List<OrderItem> orderItems) {
        return orderItems.stream()
                .map(orderItem -> toOrderItemParameterSource(orderId, orderItem))
                .toArray(SqlParameterSource[]::new);
    }

    private MapSqlParameterSource toOrderItemParameterSource(UUID orderId, OrderItem orderItem) {
        return new MapSqlParameterSource()
                .addValue("orderId", orderId.toString())
                .addValue("name", orderItem.menu().name())
                .addValue("orderCount", orderItem.orderCount())
                .addValue("price", orderItem.menu().price());
    }
}
