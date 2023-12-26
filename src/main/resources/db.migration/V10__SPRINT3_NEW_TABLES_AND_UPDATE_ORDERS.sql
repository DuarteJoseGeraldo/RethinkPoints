CREATE TABLE IF NOT EXISTS `product`
(
    `id`     BIGINT AUTO_INCREMENT PRIMARY KEY,
    `code`   VARCHAR(255) UNIQUE,
    `name`   VARCHAR(255),
    `price`  DOUBLE,
    `points` INT
);

CREATE TABLE IF NOT EXISTS `extract`
(
    `id`               BIGINT AUTO_INCREMENT PRIMARY KEY,
    `user_cpf`         VARCHAR(255) NOT NULL,
    `order_id`         VARCHAR(255),
    `partner_code`     VARCHAR(255),
    `product_code`     VARCHAR(255),
    `total`            DOUBLE,
    `points`           INT,
    `transaction_date` DATETIME,
    `transaction_type` VARCHAR(255),
    FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
    FOREIGN KEY (`partner_code`) REFERENCES `partner` (`code`),
    FOREIGN KEY (`product_code`) REFERENCES `product` (`code`)
);

ALTER TABLE `orders`
    ADD COLUMN `credit_date` TIMESTAMP NOT NULL;

