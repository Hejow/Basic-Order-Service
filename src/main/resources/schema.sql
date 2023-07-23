DROP TABLE IF EXISTS order_items;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS menus;
DROP TABLE IF EXISTS shops;

CREATE TABLE shops
(
    shop_id         VARCHAR(50) PRIMARY KEY,
    name            VARCHAR(20) NOT NULL,
    min_order_price INT         NOT NULL
);

CREATE TABLE menus
(
    menu_id BIGINT      PRIMARY KEY AUTO_INCREMENT,
    shop_id VARCHAR(50),
    name    VARCHAR(20) NOT NULL,
    price   INT         NOT NULL,
    FOREIGN KEY (shop_id) REFERENCES shops (shop_id) ON DELETE CASCADE
);

CREATE TABLE orders
(
    order_id    VARCHAR(50) PRIMARY KEY,
    shop_id     VARCHAR(50),
    total_price INT         NOT NULL,
    status      VARCHAR(20) NOT NULL,
    FOREIGN KEY (shop_id) REFERENCES shops (shop_id) ON DELETE CASCADE
);

CREATE TABLE order_items
(
    order_item_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_id      VARCHAR(50),
    name          VARCHAR(20) NOT NULL,
    count         INT         NOT NULL,
    price         INT         NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders (order_id) ON DELETE CASCADE
);

INSERT INTO shops(shop_id, name, min_order_price)
VALUES ('e59fc106-6ec9-49c3-bc7a-9fcbd2ded6bf', 'hejow-chicken', 15000);

INSERT INTO menus(shop_id, name, price)
VALUES ('e59fc106-6ec9-49c3-bc7a-9fcbd2ded6bf', 'fried_chicken', 16900);

INSERT INTO menus(shop_id, name, price)
VALUES ('e59fc106-6ec9-49c3-bc7a-9fcbd2ded6bf', 'jon_mat_chicken', 18900);

INSERT INTO menus(shop_id, name, price)
VALUES ('e59fc106-6ec9-49c3-bc7a-9fcbd2ded6bf', 'fries', 3500);
