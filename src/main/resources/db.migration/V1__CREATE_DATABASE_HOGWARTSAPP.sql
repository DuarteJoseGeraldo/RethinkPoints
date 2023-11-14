CREATE TABLE `house`
(
    id   BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255)
);
CREATE TABLE `user`
(
    id         BIGINT PRIMARY KEY AUTO_INCREMENT,
    name       VARCHAR(255),
    cpf        VARCHAR(255),
    house_id   BIGINT,
    password   VARCHAR(255),
    points     FLOAT,
    last_login TIMESTAMP,
    FOREIGN KEY (house_id) REFERENCES house (id)
);
CREATE TABLE `campaing`
(
    id             BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_campaing    VARCHAR(255),
    description    VARCHAR(255),
    our_parity     FLOAT,
    partner_parity FLOAT,
    start_at       TIMESTAMP,
    end_at         TIMESTAMP
)
