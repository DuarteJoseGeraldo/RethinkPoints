INSERT INTO "users" (id, name, cpf, house_id, password, points, last_login, user_type, status)
VALUES (2, 'Hermione Granger', '88888888888', 3, '$2a$12$4LZEYLV./z1ndy9/UMERUuNmjDeIb/UWSB7Ucqy.YOIpxnIcNVocK', 0,
        NULL, 'STANDARD', 'ACTIVE'),
       (3, 'Ron Weasley', '77777777777', 1, '$2a$12$4LZEYLV./z1ndy9/UMERUuNmjDeIb/UWSB7Ucqy.YOIpxnIcNVocK', 0, NULL,
        'STANDARD', 'ACTIVE'),
       (4, 'Luna Lovegood', '66666666666', 3, '$2a$12$4LZEYLV./z1ndy9/UMERUuNmjDeIb/UWSB7Ucqy.YOIpxnIcNVocK', 0, NULL,
        'STANDARD', 'ACTIVE'),
       (5, 'Draco Malfoy', '55555555555', 4, '$2a$12$4LZEYLV./z1ndy9/UMERUuNmjDeIb/UWSB7Ucqy.YOIpxnIcNVocK', 0, NULL,
        'STANDARD', 'ACTIVE'),
       (6, 'Ginny Weasley', '44444444444', 1, '$2a$12$4LZEYLV./z1ndy9/UMERUuNmjDeIb/UWSB7Ucqy.YOIpxnIcNVocK', 0, NULL,
        'STANDARD', 'ACTIVE'),
       (7, 'Neville Longbottom', '33333333333', 2, '$2a$12$4LZEYLV./z1ndy9/UMERUuNmjDeIb/UWSB7Ucqy.YOIpxnIcNVocK', 0,
        NULL, 'STANDARD', 'ACTIVE'),
       (8, 'Cho Chang', '22222222222', 3, '$2a$12$4LZEYLV./z1ndy9/UMERUuNmjDeIb/UWSB7Ucqy.YOIpxnIcNVocK', 0, NULL,
        'STANDARD', 'ACTIVE'),
       (9, 'Cedric Diggory', '11111111111', 2, '$2a$12$4LZEYLV./z1ndy9/UMERUuNmjDeIb/UWSB7Ucqy.YOIpxnIcNVocK', 0, NULL,
        'STANDARD', 'ACTIVE'),
       (10, 'Harry Potter', '00000000000', 4, '$2a$12$4LZEYLV./z1ndy9/UMERUuNmjDeIb/UWSB7Ucqy.YOIpxnIcNVocK', 0, NULL,
        'STANDARD', 'ACTIVE');


INSERT INTO partner (id, name, code, status, client_id, client_secret, created_at)
VALUES (1, 'Caldeirão Furado', 'CAF', 'ACTIVE', 'partner_CAF_20231227_c97d', '3dce17cbceb6435891d1e881ee252fb9', NOW()),
       (2, 'Três Vassouras', 'TV', 'ACTIVE', 'partner_TV_20231227_7a8b', 'f4b26a9d8c3f0e2c1b59a7b5c89d3e4', NOW()),
       (3, 'Gemialidades Weasley', 'GW', 'ACTIVE', 'partner_GW_20231227_5c6d', 'a1b2c3d4e5f6g7h8i9j0k1l2m3n4o5p6',
        NOW()),
       (4, 'Olivaras', 'OLV', 'ACTIVE', 'partner_OLV_20231227_1a2b', 'qazwsxedcrfvtgbyhnujmikolp123456', NOW()),
       (5, 'Madame Malkin, Moda para Bruxos', 'MMM', 'ACTIVE', 'partner_MMM_20231227_9b8a',
        '5f3e1d2c4b6a7f8e9d0c1b2a3d4e5f6', NOW()),
       (6, 'Empório das Corujas', 'EDC', 'ACTIVE', 'partner_EDC_20231227_3d2c', '1a2b3c4d5e6f7g8h9i0j1k2l3m4n5o6',
        NOW());

INSERT INTO campaign (id_campaign, description, our_parity, partner_parity, start_at, end_at, partner_id)
VALUES ('DEFAULTCAF', 'Campanha padrão para Caldeirão Furado', 1, 1, '2023-12-27 13:08:20', NULL, 1),
       ('DEFAULTTV', 'Campanha padrão para Três Vassouras', 1, 1, '2023-12-27 13:08:20', NULL, 2),
       ('DEFAULTGW', 'Campanha padrão para Gemialidades Weasley', 1, 1, '2023-12-27 13:08:20', NULL, 3),
       ('DEFAULTOLV', 'Campanha padrão para Olivaras', 1, 1, '2023-12-27 13:08:20', NULL, 4),
       ('DEFAULTMMM', 'Campanha padrão para Madame Malkin', 1, 1, '2023-12-27 13:08:20', NULL, 5),
       ('DEFAULTEDC', 'Campanha padrão para Empório das Corujas', 1, 1, '2023-12-27 13:08:20', NULL, 6),
       ('TORNTRI2024', 'Torneio Tri-Bruxo - Caldeirão Furado', 2, 1, '2023-12-31 18:00:00', '2024-02-28 23:59:59', 1),
       ('SALETV2024', 'Grande Sale - Três Vassouras', 2, 1, '2023-12-31 00:00:00', '2024-02-29 23:59:59', 2),
       ('WIZARDGW2024', 'Wizard Weekend - Gemialidades Weasley', 3, 2, '2023-12-31 00:00:00', '2024-02-29 23:59:59', 3),
       ('MAGICWANDOLV2024', 'Magic Wand Sale - Olivaras', 1, 1, '2023-12-31 00:00:00', '2024-02-29 23:59:59', 4),
       ('FASHIONMMM2024', 'Fashion Festival - Madame Malkin', 2, 1, '2023-12-31 00:00:00', '2024-02-29 23:59:59', 5),
       ('OWLBOOKEDC2024', 'Owl Book Release - Empório das Corujas', 1, 1, '2023-12-31 00:00:00', '2024-02-29 23:59:59',
        6);


INSERT INTO product (code, name, price, points)
VALUES ('NB3000', 'Ninbus 3000', 499.99, 1000),
       ('PD001', 'Varinha Mágica', 89.99, 200),
       ('PD002', 'Capa de Invisibilidade', 249.99, 500),
       ('PD003', 'Kit Poções Avançadas', 149.99, 300),
       ('PD004', 'Mapa do Maroto', 69.99, 150),
       ('PD005', 'Vira-Tempo', 199.99, 450),
       ('PD006', 'Pelúcia do Hipogrifo', 29.99, 80),
       ('PD007', 'Livro de Feitiços Avançados', 129.99, 280),
       ('PD008', 'Cálice de Fogo Réplica', 179.99, 400),
       ('PD009', 'Quadribol de Mesa', 99.99, 220),
       ('PD010', 'Pena Mágica', 9.99, 30),
       ('PD011', 'Amuleto Protetor', 49.99, 120),
       ('PD012', 'Cofre da Gringotes', 149.99, 320),
       ('PD013', 'Varinha de Sáuco', 299.99, 600),
       ('PD014', 'Bala de Gelo Florean Fortescue', 4.99, 15),
       ('PD015', 'Chapéu Seletor Réplica', 89.99, 200),
       ('PD016', 'Kit de Manutenção de Vassoura', 19.99, 60),
       ('PD017', 'Escova de Dentes Mágica', 7.99, 25),
       ('PD018', 'Relógio do Professor Dumbledore', 129.99, 280),
       ('PD019', 'Baralho de Cartas Encantadas', 14.99, 50),
       ('PD020', 'Caneca Mágica', 9.99, 30);

INSERT INTO orders (id, user_cpf, partner_code, token, order_number, order_date, status, total, change_date, points,
                    credit_date)
VALUES ('155_MMM', '11111111111', 'MMM', '8bd0166f843341db8f304e7fe37cb9b4', '155', '2023-12-27 16:00:00',
        'ORDER_CONFIRMED', 325.7, '2023-12-28 16:04:13', 651.4, '2024-12-01 01:00:00'),
       ('168_CAF', '99999999999', 'CAF', '1107e0b839d045838c8257c15f26d734', '168', '2023-12-27 16:00:00',
        'ORDER_CONFIRMED', 951.7, '2023-12-28 16:00:13', 1903.4, '2024-12-01 01:00:00'),
       ('169_CAF', '11111111111', 'CAF', 'a1c0b6164ec744b0a31ce88544d77f19', '169', '2023-12-27 16:00:00',
        'ORDER_CONFIRMED', 421.4, '2023-12-28 16:00:13', 842.8, '2024-12-01 01:00:00'),
       ('198_TV', '33333333333', 'TV', '0336b3dda28c4f84ac2a32e0add72134', '198', '2023-12-27 16:00:00',
        'ORDER_CONFIRMED', 325.7, '2023-12-28 16:00:13', 651.4, '2024-12-01 01:00:00');

INSERT INTO item (id, order_id, sku, price, quantity)
VALUES (1, '155_MMM', 'tb6', 22.4, 2),
       (1, '168_CAF', '4rw', 7.9, 3),
       (1, '169_CAF', '4rw', 7.9, 2),
       (1, '198_TV', 'tb6', 22.4, 2),
       (2, '155_MMM', 'fg6', 80.9, 1),
       (2, '168_CAF', 'a8d', 5.6, 5),
       (2, '169_CAF', 'a8d', 5.6, 1),
       (2, '198_TV', 'fg6', 80.9, 1),
       (3, '155_MMM', '67j', 100, 2),
       (3, '168_CAF', 'aa2', 100, 9),
       (3, '169_CAF', 'aa2', 100, 4),
       (3, '198_TV', '67j', 100, 2);