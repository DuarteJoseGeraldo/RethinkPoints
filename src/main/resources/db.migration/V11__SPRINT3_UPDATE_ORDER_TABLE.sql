ALTER TABLE `orders`
    MODIFY COLUMN `status` ENUM('WAITING_CONFIRMATION', 'ORDER_CANCELED', 'ORDER_CONFIRMED', 'ORDER_CREDITED') NOT NULL;