DROP TABLE IF EXISTS order_items;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS menus;
DROP TABLE IF EXISTS shops;

CREATE TABLE shops
(
    id              VARCHAR(30) PRIMARY KEY,
    name            VARCHAR(20) NOT NULL,
    min_order_price INT         NOT NULL
);

CREATE TABLE menus
(
    id       BIGINT      NOT NULL PRIMARY KEY AUTO_INCREMENT,
    shop_id  VARCHAR(30),
    name     VARCHAR(20) NOT NULL,
    price    INT         NOT NULL,
    FOREIGN KEY (shop_id) REFERENCES shops (id) ON DELETE CASCADE
);

CREATE TABLE orders
(
    id          VARCHAR(30) PRIMARY KEY,
    shop_id     VARCHAR(30),
    total_price INT         NOT NULL,
    status      VARCHAR(20) NOT NULL,
    FOREIGN KEY (shop_id) REFERENCES shops (id) ON DELETE CASCADE
);

CREATE TABLE order_items
(
    id       BIGINT      NOT NULL PRIMARY KEY AUTO_INCREMENT,
    order_id VARCHAR(30),
    name     VARCHAR(20) NOT NULL,
    count    INT         NOT NULL,
    price    INT         NOT NULL,
    INDEX (order_id),
    FOREIGN KEY (order_id) REFERENCES orders (id) ON DELETE CASCADE
);
