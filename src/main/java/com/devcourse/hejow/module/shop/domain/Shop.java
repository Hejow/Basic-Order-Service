package com.devcourse.hejow.module.shop.domain;

import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
public class Shop {
    private final UUID id;
    private final String name;
    private final List<Menu> menus;

    Shop(UUID id, String name, List<Menu> menus) {
        this.id = id;
        this.name = name;
        this.menus = menus;
    }
}
