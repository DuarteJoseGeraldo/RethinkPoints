CREATE TABLE partner
(
    id            SERIAL PRIMARY KEY,
    name          VARCHAR(255),
    code          VARCHAR(255) UNIQUE,
    status        VARCHAR(255) DEFAULT 'ACTIVE' NOT NULL CHECK (status IN ('ACTIVE', 'INACTIVE')),
    client_id     VARCHAR(255) UNIQUE,
    client_secret VARCHAR(255),
    created_at    TIMESTAMP
);

ALTER TABLE campaign
    ADD COLUMN partner_id BIGINT;

ALTER TABLE campaign
    ADD CONSTRAINT fk_campaign_partner
        FOREIGN KEY (partner_id)
            REFERENCES partner (id);

CREATE TABLE access_token
(
    user_identifier VARCHAR(255) PRIMARY KEY,
    token           VARCHAR(255) NOT NULL,
    login_type      VARCHAR(255) DEFAULT 'COMMON_AUTH' NOT NULL CHECK (login_type IN ('COMMON_AUTH', 'CLIENT_CREDENTIALS')),
    created_at      TIMESTAMP
);

CREATE TABLE hotsite
(
    token              VARCHAR(255) PRIMARY KEY,
    cpf                VARCHAR(255) NOT NULL,
    id_campaign        VARCHAR(255) NOT NULL,
    partner_code       VARCHAR(255) NOT NULL,
    click_date         TIMESTAMP NOT NULL,
    order_confirmation BOOLEAN DEFAULT false
);
