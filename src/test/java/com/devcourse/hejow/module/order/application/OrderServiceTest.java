package com.devcourse.hejow.module.order.application;

import com.devcourse.hejow.global.exception.ErrorCode;
import com.devcourse.hejow.global.exception.common.ValidationFailException;
import com.devcourse.hejow.module.order.application.dto.GetShopOrderResponse;
import com.devcourse.hejow.module.order.domain.Order;
import com.devcourse.hejow.module.order.domain.OrderItem;
import com.devcourse.hejow.module.order.domain.repository.OrderRepository;
import com.devcourse.hejow.module.shop.application.ShopService;
import com.devcourse.hejow.module.shop.domain.Menu;
import com.devcourse.hejow.module.shop.domain.Shop;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.devcourse.hejow.global.exception.ErrorCode.EMPTY_ORDER;
import static com.devcourse.hejow.global.exception.ErrorCode.LESS_THAN_MIN_AMOUNT;
import static com.devcourse.hejow.global.exception.ErrorCode.NOT_SUPPORT_MENU;
import static com.devcourse.hejow.module.order.domain.Order.Status.ORDERED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.times;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willReturn;

@ExtendWith(SpringExtension.class)
class OrderServiceTest {
    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ShopService shopService;

    @Mock
    private Shop shop;

    private final UUID orderId = UUID.randomUUID();;
    private final UUID shopId = UUID.randomUUID();;

    @Test
    @DisplayName("매장 아이디로 주문 내역을 불러오면 DTO로 변환된 리스트로 반환되어야 한다.")
    void getAllOrderByShopTest() {
        // given
        int price = 1_000;
        Order order = new Order(orderId, shop.getId(), List.of(), 0, ORDERED);

        willReturn(List.of(order)).given(orderRepository).findAllOrderByShopId(any());

        // when
        List<GetShopOrderResponse> responses = orderService.getAllOrderByShop(shop.getId());

        // then
        then(orderRepository).should(times(1)).findAllOrderByShopId(any());
        assertThat(responses).isNotEmpty().hasSize(1);

        GetShopOrderResponse response = responses.get(0);
        assertThat(response.id()).isEqualTo(order.getId());
        assertThat(response.totalPrice()).isEqualTo(order.getTotalPrice());
    }

    @Nested
    @DisplayName("주문 생성 테스트")
    class createTest {
        private final UUID shopId = UUID.randomUUID();
        private final List<OrderItem> orderItems = List.of();
        private final int price = 10_000;

        @Test
        @DisplayName("주문 생성 요청을 하면 orderRepository와 shopService를 각 한번씩 호출해야 한다.")
        void create_Success() {
            // given
            willReturn(shop).given(shopService).findById(any());
            willDoNothing().given(shop).validateOrder(anyList(), anyInt());

            // when
            orderService.create(shopId, orderItems);

            // then
            then(orderRepository).should(times(1)).save(any(), anyList(), anyInt());
            then(shopService).should(times(1)).findById(any());
            then(shop).should(times(1)).validateOrder(anyList(), anyInt());
        }

        @Test
        @DisplayName("주문 항목들이 없으면 ValidationFailException 예외를 던져야 한다.")
        void create_Fail_ByEmptyOrderItems() {
            // given
            Shop fakeShop = new Shop(shopId, "", List.of(), 0);
            willReturn(fakeShop).given(shopService).findById(any());

            // when, then
            assertThatExceptionOfType(ValidationFailException.class)
                    .isThrownBy(() -> orderService.create(shopId, orderItems))
                    .withMessage(EMPTY_ORDER.getMessage());
        }

        @Test
        @DisplayName("총 금액이 최소 주문 금액을 넘지 않으면 ValidationFailException 예외를 던져야 한다.")
        void create_Fail_ByLessMinimumOrderAmount() {
            // given
            int orderCount = 2;
            int minimumOrderPrice = 100_000;

            Menu menu = new Menu("chicken", price);
            List<OrderItem> orderItems = List.of(new OrderItem(menu, orderCount));

            Shop fakeShop = new Shop(shopId, "", List.of(), minimumOrderPrice);
            willReturn(fakeShop).given(shopService).findById(any());

            // when, then
            assertThatExceptionOfType(ValidationFailException.class)
                    .isThrownBy(() -> orderService.create(shopId, orderItems))
                    .withMessage(LESS_THAN_MIN_AMOUNT.getMessage());
        }

        @Test
        @DisplayName("매장에 없는 메뉴라면 ValidationFailException 예외를 던져야 한다.")
        void create_Fail_ByNotSupportMenu() {
            // given
            int orderCount = 1;
            int minimumOrderPrice = 0;
            Menu supportMenu = new Menu("chicken", price);
            Menu notSupportMenu = new Menu("another-chicken", price);
            List<OrderItem> orderItems = List.of(new OrderItem(notSupportMenu, orderCount));

            Shop fakeShop = new Shop(shopId, "", List.of(supportMenu), minimumOrderPrice);
            willReturn(fakeShop).given(shopService).findById(any());

            // when, then
            assertThatExceptionOfType(ValidationFailException.class)
                    .isThrownBy(() -> orderService.create(shopId, orderItems))
                    .withMessage(NOT_SUPPORT_MENU.getMessage());
        }
    }

    @Nested
    @DisplayName("주문 시작 테스트")
    class startDeliveryTest {
        @Test
        @DisplayName("주문 완료 상태에서 주문 시작을 하면 Repository를 1번만 호출해야 한다.")
        void startDelivery_Success() {
            // given
            Order order = new Order(orderId, shop.getId(), List.of(), 0, ORDERED);

            willReturn(Optional.of(order)).given(orderRepository).findById(any());

            // when
            orderService.startDelivery(order.getId());

            // then
            then(orderRepository).should(times(1)).findById(any());
        }

        @ParameterizedTest
        @DisplayName("주문 완료 이외의 상태에서 주문 시작을 하면 ValidationFailException 예외를 던져야 한다.")
        @CsvSource(value = {
                "DELIVERING, ALREADY_DELIVERY_STARTED",
                "CANCELED, ALREADY_CANCELED"})
        void startDelivery_Fail_ByNoneOrderedStatus(Order.Status status, ErrorCode errorCode) {
            // given
            Order order = new Order(orderId, shop.getId(), List.of(), 0, status);

            willReturn(Optional.of(order)).given(orderRepository).findById(any());

            // when, then
            assertThatExceptionOfType(ValidationFailException.class)
                    .isThrownBy(() -> orderService.startDelivery(order.getId()))
                    .withMessage(errorCode.getMessage());
        }
    }

    @Nested
    @DisplayName("주문 취소 테스트")
    class cancelOrderTest {
        @Test
        @DisplayName("주문 완료 상태에서 주문 취소을 하면 정상적으로 취소되고 Repository를 한번만 호출해야 한다.")
        void cancelOrder_Success() {
            UUID id = UUID.randomUUID();
            Order order = new Order(orderId, shop.getId(), List.of(), 0, ORDERED);

            willReturn(Optional.of(order)).given(orderRepository).findById(any());

            // when
            orderService.cancelOrder(id);

            // then
            then(orderRepository).should(times(1)).findById(any());
        }

        @ParameterizedTest
        @DisplayName("배달 상태와 취소 상태에서 주문 취소하면 ValidationFailException 예외를 던져야 한다.")
        @CsvSource(value = {
                "DELIVERING, ALREADY_DELIVERY_STARTED",
                "CANCELED, ALREADY_CANCELED"})
        void cancelOrder_Fail_ByAlreadyDeliveringOrCanceled(Order.Status status, ErrorCode errorCode) {
            Order order = new Order(orderId, shop.getId(), List.of(), 0, status);

            willReturn(Optional.of(order)).given(orderRepository).findById(any());

            // when, then
            assertThatExceptionOfType(ValidationFailException.class)
                    .isThrownBy(() -> orderService.cancelOrder(order.getId()))
                    .withMessage(errorCode.getMessage());
        }
    }
}
