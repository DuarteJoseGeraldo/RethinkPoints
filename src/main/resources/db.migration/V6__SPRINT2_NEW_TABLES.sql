CREATE TABLE partner
(
    id            BIGINT NOT NULL AUTO_INCREMENT,
    name          VARCHAR(255),
    code          VARCHAR(255) UNIQUE,
    status        ENUM('ACTIVE', 'INACTIVE'),
    client_id     VARCHAR(255) UNIQUE,
    client_secret VARCHAR(255),
    created_at    TIMESTAMP,
    PRIMARY KEY (id)
);
ALTER TABLE campaign
    ADD COLUMN partner_id BIGINT,
ADD CONSTRAINT fk_campaign_partner
    FOREIGN KEY (partner_id)
    REFERENCES partner (id);

CREATE TABLE access_token
(
    user_identifier VARCHAR(255),
    token           VARCHAR(255) NOT NULL,
    login_type      ENUM('COMMON_AUTH', 'CLIENT_CREDENTIALS') DEFAULT 'COMMON_AUTH' NOT NULL,
    created_at      TIMESTAMP,
    PRIMARY KEY (user_identifier)
);
CREATE TABLE hotsite
(
    token              VARCHAR(255) UNIQUE,
    cpf                VARCHAR(255) NOT NULL,
    id_campaign        VARCHAR(255) NOT NULL,
    partner_code       VARCHAR(255) NOT NULL,
    click_date         TIMESTAMP    NOT NULL,
    order_confirmation BOOLEAN DEFAULT false,
    PRIMARY KEY (token)
);