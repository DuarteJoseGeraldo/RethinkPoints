CREATE TABLE IF NOT EXISTS "orders"
(
    "id"           VARCHAR(255)        NOT NULL,
    "user_cpf"     VARCHAR(255)        NOT NULL,
    "partner_code" VARCHAR(255)        NOT NULL,
    "token"        VARCHAR(255)        NOT NULL,
    "order_number" VARCHAR(255) UNIQUE NOT NULL,
    "order_date"   TIMESTAMP           NOT NULL,
    "status"       VARCHAR(255) NOT NULL CHECK (status IN ('WAITING_CONFIRMATION', 'ORDER_CANCELED', 'ORDER_CONFIRMED','ORDER_CREDITED')),
    "total"        DOUBLE PRECISION,
    "change_date"  TIMESTAMP,
    "points"       REAL                NOT NULL,
    PRIMARY KEY ("id")
);

CREATE TABLE IF NOT EXISTS "item"
(
    "id"       VARCHAR(255)     NOT NULL,
    "order_id" VARCHAR(255)     NOT NULL,
    "sku"      VARCHAR(255)     NOT NULL,
    "price"    DOUBLE PRECISION NOT NULL,
    "quantity" INT              NOT NULL,
    PRIMARY KEY ("id", "order_id"),
    FOREIGN KEY ("order_id") REFERENCES "orders" ("id")
);