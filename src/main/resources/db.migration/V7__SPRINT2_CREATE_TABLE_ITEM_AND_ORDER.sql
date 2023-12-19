CREATE TABLE IF NOT EXISTS `order` (
    `id` VARCHAR(255) NOT NULL,
    `user_cpf` VARCHAR(255) NOT NULL,
    `partner_code` VARCHAR(255) NOT NULL,
    `token` VARCHAR(255) NOT NULL,
    `order_number` VARCHAR(255) UNIQUE NOT NULL,
    `order_date` DATETIME NOT NULL,
    `status` VARCHAR(255) NOT NULL,
    `total` DOUBLE,
    `change_date` DATETIME,
    `points` FLOAT NOT NULL,
    PRIMARY KEY (`id`)
    );

CREATE TABLE IF NOT EXISTS `item` (
    `id` VARCHAR(255) NOT NULL,
    `order_id` VARCHAR(255) NOT NULL,
    `sku` VARCHAR(255) NOT NULL,
    `price` DOUBLE NOT NULL,
    `quantity` INT NOT NULL,
    PRIMARY KEY (`id`, `order_id`),
    FOREIGN KEY (`order_id`) REFERENCES `order` (`id`)
    );