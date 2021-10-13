DROP TABLE IF EXISTS BOOKS;
DROP TABLE IF EXISTS CART;
DROP TABLE IF EXISTS WAREHOUSE;
DROP TABLE IF EXISTS ORDERS;

CREATE TABLE BOOKS
(
    id           INT AUTO_INCREMENT PRIMARY KEY,
    product_type VARCHAR(250)  NOT NULL,
    product_name VARCHAR(250)  NOT NULL,
    author       VARCHAR(250)  NOT NULL,
    price        INT           NOT NULL,
    img          VARCHAR(250)  NOT NULL,
    about        VARCHAR(1000) NOT NULL
);

CREATE TABLE CART
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    id_products INT NOT NULL,
    count       INT NOT NULL
);

CREATE TABLE WAREHOUSE
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    id_products INT NOT NULL,
    count       INT NOT NULL
);

CREATE TABLE ORDERS
(
    id          INT NOT NULL,
    id_products INT NOT NULL,
    count       INT NOT NULL
);