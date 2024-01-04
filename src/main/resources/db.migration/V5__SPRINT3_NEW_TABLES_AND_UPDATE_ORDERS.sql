
CREATE TABLE IF NOT EXISTS product
(
    id     BIGSERIAL PRIMARY KEY,
    code   VARCHAR(255) UNIQUE,
    name   VARCHAR(255),
    price  DOUBLE PRECISION,
    points INTEGER
);


CREATE TABLE IF NOT EXISTS extract
(
    id               BIGSERIAL PRIMARY KEY,
    user_cpf         VARCHAR(255) NOT NULL,
    order_id         VARCHAR(255),
    partner_code     VARCHAR(255),
    product_code     VARCHAR(255),
    total            DOUBLE PRECISION,
    points           INTEGER,
    transaction_date TIMESTAMP,
    transaction_type VARCHAR(255),
    FOREIGN KEY (order_id) REFERENCES orders (id),
    FOREIGN KEY (partner_code) REFERENCES partner (code),
    FOREIGN KEY (product_code) REFERENCES product (code)
);

ALTER TABLE orders
    ADD COLUMN credit_date TIMESTAMP;