package com.devcourse.hejow.module.order.domain.repository;

import com.devcourse.hejow.module.order.domain.Order;
import com.devcourse.hejow.module.order.domain.OrderItem;
import com.devcourse.hejow.module.shop.domain.Menu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.devcourse.hejow.module.order.domain.Order.Status.CANCELED;
import static com.devcourse.hejow.module.order.domain.Order.Status.DELIVERING;
import static com.devcourse.hejow.module.order.domain.Order.Status.ORDERED;
import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@ContextConfiguration(classes = OrderRepositoryImpl.class)
class OrderRepositoryImplTest {
    @Autowired
    private OrderRepository orderRepository;

    @Test
    @DisplayName("생성하면 id 생성되고 주문 완료 상태가 되며 주문 상품들도 저장되어야 한다.")
    void saveTest() {
        // given
        int price = 15000;
        int orderCount = 2;
        int totalPrice = price * orderCount;
        UUID shopId = UUID.randomUUID();

        Menu menu = new Menu("chicken", price);
        OrderItem orderItem = new OrderItem(menu, orderCount);

        // when
        UUID orderId = orderRepository.save(null, List.of(orderItem), totalPrice);

        // then
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        assertThat(orderOptional).isNotEmpty();

        Order order = orderOptional.get();
        assertThat(order.getId()).isEqualTo(orderId);
//        assertThat(order.getShopId()).isEqualTo(shopId);
        assertThat(order.getStatus()).isEqualTo(ORDERED);
        assertThat(order.getTotalPrice()).isEqualTo(totalPrice);
        assertThat(order.getOrderItems()).isNotEmpty().hasSize(1);

        OrderItem savedOrderItem = order.getOrderItems().get(0);
        assertThat(savedOrderItem.menu()).isEqualTo(menu);
        assertThat(savedOrderItem.orderCount()).isEqualTo(orderCount);
    }

    @Nested
    @DisplayName("상태 업데이트 테스트")
    class updateStatusTest {
        private UUID orderId;

        @BeforeEach
        void initOrder() {
            orderId = orderRepository.save(null, List.of(), 0);
        }

        @Test
        @DisplayName("주문이 시작되면 상태가 DELIVERING으로 변경되어야 한다.")
        void startDeliveryTest() {
            // given

            // when
            orderRepository.startDelivery(orderId);

            // then
            Optional<Order> orderOptional = orderRepository.findById(orderId);
            assertThat(orderOptional).isNotEmpty();

            Order order = orderOptional.get();
            assertThat(order.getStatus()).isEqualTo(DELIVERING);
        }

        @Test
        @DisplayName("주문을 취소하면 상태가 CANCELED으로 변경되어야 한다.")
        void cancelOrderTest() {
            // given

            // when
            orderRepository.cancelOrder(orderId);

            // then
            Optional<Order> orderOptional = orderRepository.findById(orderId);
            assertThat(orderOptional).isNotEmpty();

            Order order = orderOptional.get();
            assertThat(order.getStatus()).isEqualTo(CANCELED);
        }
    }
}
