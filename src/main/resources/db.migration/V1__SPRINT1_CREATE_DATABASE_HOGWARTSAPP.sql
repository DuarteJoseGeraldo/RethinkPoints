CREATE TABLE hub
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE address
(
    id           SERIAL PRIMARY KEY,
    street       VARCHAR(255) NOT NULL ,
    number       INT NOT NULL,
    neighborhood VARCHAR(255) NOT NULL,
    complement   VARCHAR(255),
    city         VARCHAR(255) NOT NULL,
    state        VARCHAR(50) NOT NULL,
    zip_code     VARCHAR(10) NOT NULL
);

CREATE TABLE users
(
    id         BIGSERIAL PRIMARY KEY,
    name       VARCHAR(255),
    cpf        VARCHAR(255) UNIQUE,
    hub_id     BIGINT NOT NULL ,
    address_id BIGINT NOT NULL ,
    password   VARCHAR(255),
    points     DOUBLE PRECISION,
    user_type  VARCHAR(255) DEFAULT 'STANDARD' NOT NULL CHECK (user_type IN ('STANDARD', 'ADMIN')),
    last_login TIMESTAMP,
    status     VARCHAR(255) DEFAULT 'ACTIVE'   NOT NULL CHECK (status IN ('ACTIVE', 'INACTIVE')),
    FOREIGN KEY (hub_id) REFERENCES hub (id),
    FOREIGN KEY (address_id) REFERENCES address (id)
);

CREATE TABLE campaign
(
    id             BIGSERIAL PRIMARY KEY,
    id_campaign    VARCHAR(255) UNIQUE,
    description    VARCHAR(255),
    our_parity     DOUBLE PRECISION,
    partner_parity DOUBLE PRECISION,
    start_at       TIMESTAMP,
    end_at         TIMESTAMP
);
