package com.devcourse.hejow.module.shop.api;

import com.devcourse.hejow.module.shop.api.dto.CreateMenuRequest;
import com.devcourse.hejow.module.shop.api.dto.CreateShopRequest;
import com.devcourse.hejow.module.shop.application.ShopService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ShopController.class)
class ShopControllerTest {
    private static final String BASE_URI = "/shops";

    @MockBean
    private ShopService shopService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    String name = "hejow";
    int price = 1_5000;

    @Test
    @DisplayName("생성 요청 시 201 응답과 함께 서비스가 한번만 호출되어야 한다.")
    void createTest() throws Exception {
        // given
        CreateShopRequest request = new CreateShopRequest(name, price);

        given(shopService.create(any(), anyInt())).willReturn(UUID.randomUUID());

        // when
        mockMvc.perform(post(BASE_URI)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andDo(print());

        // then
        then(shopService).should(times(1)).create(any(), anyInt());
    }

    @Test
    @DisplayName("메뉴 생성 요청 시 201 응답과 함께 서비스가 한번만 호출되어야 한다.")
    void createMenuTest() throws Exception {
        // given
        UUID id = UUID.randomUUID();
        CreateMenuRequest request = new CreateMenuRequest(name, price);

        willDoNothing().given(shopService).newMenu(any(), any(), anyInt());

        // when
        mockMvc.perform(post(BASE_URI + "/" + id + "/menus")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andDo(print());

        // then
        then(shopService).should(times(1)).newMenu(any(), any(), anyInt());
    }
}
