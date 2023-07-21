package com.devcourse.hejow.module.shop.application;

import com.devcourse.hejow.module.shop.domain.repository.ShopRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.times;
import static org.mockito.BDDMockito.willDoNothing;

@ExtendWith(SpringExtension.class)
class ShopServiceTest {
    @InjectMocks
    private ShopService shopService;

    @Mock
    private ShopRepository shopRepository;

    @Test
    @DisplayName("create를 호출하면 Repository가 한번만 호출해야 한다.")
    void createTest() {
        // given
        String name = "hejow";
        int price = 1_000;

        given(shopRepository.save(any(), anyInt())).willReturn(UUID.randomUUID());

        // when
        shopService.create(name, price);

        // then
        then(shopRepository).should(times(1)).save(any(), anyInt());
    }

    @Test
    @DisplayName("newMenu를 호출하면 Repository가 한번만 호출해야 한다.")
    void newMenuTest() {
        // given
        UUID id = UUID.randomUUID();
        String chicken = "chicken";
        int price = 1_00;

        willDoNothing().given(shopRepository).saveMenu(any(), any());

        // when
        shopService.newMenu(id, chicken, price);

        // then
        then(shopRepository).should(times(1)).saveMenu(any(), any());
    }
}
