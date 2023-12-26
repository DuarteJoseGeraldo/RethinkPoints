INSERT INTO `house` (name) VALUES ('Gryffindor');

SET @lastHouseId = LAST_INSERT_ID();

INSERT INTO `user` (name, cpf, house_id, password, points, user_type, status)
VALUES ('Albus Dumbledore', '99999999999', @lastHouseId, '$2a$12$4LZEYLV./z1ndy9/UMERUuNmjDeIb/UWSB7Ucqy.YOIpxnIcNVocK', 0, 'ADMIN', 'ACTIVE');