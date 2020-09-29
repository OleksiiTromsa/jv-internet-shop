CREATE SCHEMA `internet_shop` DEFAULT CHARACTER SET utf8;

CREATE TABLE `internet_shop`.`products` (
   `product_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
   `product_name` VARCHAR(255) NOT NULL,
   `price` BIGINT(11) NOT NULL,
   `deleted` TINYINT(1) NOT NULL DEFAULT 0,
    PRIMARY KEY (`product_id`));

CREATE TABLE `internet_shop`.`roles` (
   `role_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
   `role_name` VARCHAR(255) NOT NULL,
   `deleted` TINYINT(1) NOT NULL DEFAULT 0,
    PRIMARY KEY (`role_id`));

INSERT INTO `internet_shop`.`roles`
(`role_name`)
VALUES ('USER');
INSERT INTO `internet_shop`.`roles`
(`role_name`)
VALUES ('ADMIN');

CREATE TABLE `internet_shop`.`users` (
    `user_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
    `user_name` VARCHAR(255) NOT NULL,
    `login` VARCHAR(255) NOT NULL,
    `password` VARCHAR(255) NOT NULL,
    `salt` VARBINARY(16) NOT NULL,
    `deleted` TINYINT(1) NOT NULL DEFAULT 0,
     PRIMARY KEY (`user_id`));

CREATE TABLE `internet_shop`.`shopping_carts` (
    `cart_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
    `user_id` BIGINT(11) NOT NULL,
    `deleted` TINYINT(1) NOT NULL DEFAULT 0,
    PRIMARY KEY (`cart_id`),
    INDEX `shopping_carts_users_fk_idx` (`user_id` ASC) VISIBLE,
    CONSTRAINT `shopping_carts_users_fk`
        FOREIGN KEY (`user_id`)
            REFERENCES `internet_shop`.`users` (`user_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION);

CREATE TABLE `internet_shop`.`orders` (
    `order_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
    `user_id` BIGINT(11) NOT NULL,
    `deleted` TINYINT(1) NOT NULL DEFAULT 0,
    PRIMARY KEY (`order_id`),
    INDEX `orders_users_fk_idx` (`user_id` ASC) VISIBLE,
    CONSTRAINT `orders_users_fk`
        FOREIGN KEY (`user_id`)
            REFERENCES `internet_shop`.`users` (`user_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION);

CREATE TABLE `internet_shop`.`users_roles` (
    `user_id` BIGINT(11) NOT NULL,
    `role_id` BIGINT(11) NOT NULL,
    INDEX `users_roles_users_fk_idx` (`user_id` ASC) VISIBLE,
    INDEX `users_roles_roles_fk_idx` (`role_id` ASC) VISIBLE,
    CONSTRAINT `users_roles_users_fk`
        FOREIGN KEY (`user_id`)
            REFERENCES `internet_shop`.`users` (`user_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `users_roles_roles_fk`
        FOREIGN KEY (`role_id`)
            REFERENCES `internet_shop`.`roles` (`role_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION);

CREATE TABLE `internet_shop`.`carts_products` (
`cart_id` BIGINT(11) NOT NULL,
`product_id` BIGINT(11) NOT NULL,
INDEX `carts_users_shopping_carts_fk_idx` (`cart_id` ASC) VISIBLE,
INDEX `carts_products_products_fk_idx` (`product_id` ASC) VISIBLE,
CONSTRAINT `carts_products_shopping_carts_fk`
    FOREIGN KEY (`cart_id`)
        REFERENCES `internet_shop`.`shopping_carts` (`cart_id`)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION,
CONSTRAINT `carts_products_products_fk`
    FOREIGN KEY (`product_id`)
        REFERENCES `internet_shop`.`products` (`product_id`)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION);

CREATE TABLE `internet_shop`.`orders_products` (
    `order_id` BIGINT(11) NOT NULL,
    `product_id` BIGINT(11) NOT NULL,
    INDEX `orders_products_orders_fk_idx` (`order_id` ASC) VISIBLE,
    INDEX `orders_products_products_fk_idx` (`product_id` ASC) VISIBLE,
    CONSTRAINT `orders_products_orders_fk`
        FOREIGN KEY (`order_id`)
            REFERENCES `internet_shop`.`orders` (`order_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `orders_products_products_fk`
        FOREIGN KEY (`product_id`)
            REFERENCES `internet_shop`.`products` (`product_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION);
