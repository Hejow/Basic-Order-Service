package com.devcourse.hejow.module.shop.domain;

import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
public class Shop {
    private final UUID id;
    private String name;
    private final Address address;
    private final List<Menu> menus;

    public Shop(UUID id, String name, Address address, List<Menu> menus) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.menus = menus;
    }
}
