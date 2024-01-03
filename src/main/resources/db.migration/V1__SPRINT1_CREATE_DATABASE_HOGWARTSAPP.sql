CREATE TABLE house
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(255)
);

CREATE TABLE users
(
    id         BIGSERIAL PRIMARY KEY,
    name       VARCHAR(255),
    cpf        VARCHAR(255) UNIQUE,
    house_id   BIGINT,
    password   VARCHAR(255),
    points     DOUBLE PRECISION,
    user_type VARCHAR(255) DEFAULT 'STANDARD' NOT NULL CHECK (user_type IN ('STANDARD', 'ADMIN')),
    last_login TIMESTAMP,
    status VARCHAR(255) DEFAULT 'ACTIVE' NOT NULL CHECK (status IN ('ACTIVE', 'INACTIVE')),
    FOREIGN KEY (house_id) REFERENCES house (id)
);

CREATE TABLE campaign
(
    id             BIGSERIAL PRIMARY KEY,
    id_campaign    VARCHAR(255) UNIQUE ,
    description    VARCHAR(255),
    our_parity     DOUBLE PRECISION,
    partner_parity DOUBLE PRECISION,
    start_at       TIMESTAMP,
    end_at         TIMESTAMP
);
