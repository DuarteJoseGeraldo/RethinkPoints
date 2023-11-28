ALTER TABLE user
DROP
COLUMN last_valid_token;

ALTER TABLE user
    ADD CONSTRAINT uk_cpf UNIQUE (cpf);
