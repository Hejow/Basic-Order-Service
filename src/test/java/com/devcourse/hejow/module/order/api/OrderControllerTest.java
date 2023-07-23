package com.devcourse.hejow.module.order.api;

import com.devcourse.hejow.module.order.api.dto.OrderRequest;
import com.devcourse.hejow.module.order.application.OrderService;
import com.devcourse.hejow.module.order.domain.OrderItem;
import com.devcourse.hejow.module.shop.domain.Menu;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.times;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
class OrderControllerTest {
    private static final String BASE_URI = "/orders";

    @MockBean
    private OrderService orderService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private UUID id = UUID.randomUUID();

    @Test
    @DisplayName("모든 주문 조회 시 200 응답과 함께 예상한 값이 나와야 한다.")
    void getAllOrderByShopTest() throws Exception {
        // given
        willReturn(List.of()).given(orderService).getAllOrderByShop(any());

        // when
        mockMvc.perform(get(BASE_URI + "/" + id)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.payload").isArray())
                .andExpect(jsonPath("$.payload").isEmpty())
                .andDo(print());

        // then
        then(orderService).should(times(1)).getAllOrderByShop(any());
    }

    @Test
    @DisplayName("주문하면 201 응답과 함께 서비스가 한번만 호출되어야 한다.")
    void createOrderTest() throws Exception {
        // given
        Menu menu = new Menu("chicken", 15000);
        OrderItem orderItem = new OrderItem(menu, 2);
        OrderRequest request = new OrderRequest(List.of(orderItem));

        willReturn(id).given(orderService).create(any(), anyList());

        // when
        mockMvc.perform(post(BASE_URI + "/" + id)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andDo(print());

        // then
        then(orderService).should(times(1)).create(any(), anyList());
    }

    @Test
    @DisplayName("배달 시작 요청을 보내면 200응답과 함께 서비스가 한번만 호출되어야 한다.")
    void startDeliveryTest() throws Exception {
        // given
        willDoNothing().given(orderService).startDelivery(any());

        // when
        mockMvc.perform(patch(BASE_URI + "/" + id + "/delivery")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

        // then
        then(orderService).should(times(1)).startDelivery(any());
    }

    @Test
    @DisplayName("취소 요청을 보내면 200응답과 함께 서비스가 한번만 호출되어야 한다.")
    void cancelOrderTest() throws Exception {
        // given
        willDoNothing().given(orderService).cancelOrder(any());

        // when
        mockMvc.perform(patch(BASE_URI + "/" + id + "/cancel")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

        // then
        then(orderService).should(times(1)).cancelOrder(any());
    }
}
