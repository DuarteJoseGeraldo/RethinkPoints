INSERT INTO partner (name, code, status, client_id, client_secret, created_at)
VALUES ('Caju Beneficios', 'CAJ', 'ACTIVE', 'partner_CAJ_20240109_c97d', '3dce17cbceb6435891d1e881ee252fb9',
        '2024-09-01 12:00:00'),
       ('iFood', 'IFD', 'ACTIVE', 'partner_IFD_20240109_7a8b', 'f4b26a9d8c3f0e2c1b59a7b5c89d3e4',
        '2024-09-01 12:00:00'),
       ('McDonald''s', 'MCD', 'ACTIVE', 'partner_RET_20240109_5c6d', 'a1b2c3d4e5f6g7h8i9j0k1l2m3n4o5p6',
        '2024-09-01 12:00:00'),
       ('Gol Linhas Aereas', 'GOL', 'ACTIVE', 'partner_GOL_20240109_1a2b', 'qazwsxedcrfvtgbyhnujmikolp123456', '2024-09-01 12:00:00'),
       ('Santander', 'STD', 'ACTIVE', 'partner_STD_20240109_9b8a',
        '5f3e1d2c4b6a7f8e9d0c1b2a3d4e5f6', '2024-09-01 12:00:00'),
       ('Amazon', 'AMZ', 'ACTIVE', 'partner_AMZ_20240109_3d2c', '1a2b3c4d5e6f7g8h9i0j1k2l3m4n5o6',
        '2024-09-01 12:00:00');

INSERT INTO campaign (id_campaign, description, our_parity, partner_parity, start_at, end_at, partner_id)
VALUES ('DEFAULTCAJ', 'Campanha padrão para Caju Beneficios', 1, 1, '2023-12-27 13:08:20', NULL, 1),
       ('DEFAULTIFD', 'Campanha padrão para iFood', 1, 1, '2023-12-27 13:08:20', NULL, 2),
       ('DEFAULTMCD', 'Campanha padrão para McDonald''s', 1, 1, '2023-12-27 13:08:20', NULL, 3),
       ('DEFAULTGOL', 'Campanha padrão para Gol Linhas Aereas', 1, 1, '2023-12-27 13:08:20', NULL, 4),
       ('DEFAULTMMM', 'Campanha padrão para Santander', 1, 1, '2023-12-27 13:08:20', NULL, 5),
       ('DEFAULTAMZ', 'Campanha padrão para Amazon', 1, 1, '2023-12-27 13:08:20', NULL, 6),
       ('ILOVECAJU', 'Campanha de pontos - Caju Beneficios', 2, 1, '2023-12-31 18:00:00', '2024-02-28 23:59:59', 1),
       ('RANGODAMADRUGA', 'Campanha de pontos - iFood', 2, 1, '2023-12-31 00:00:00', '2024-02-29 23:59:59', 2),
       ('SORVETIM', 'Campanha de pontos - McDonald''s', 3, 2, '2023-12-31 00:00:00', '2024-02-29 23:59:59', 3),
       ('NOVOAPP', 'Campanha de pontos - Gol Linhas Aereas', 1, 1, '2023-12-31 00:00:00', '2024-02-29 23:59:59', 4),
       ('ESFERAAPP', 'Campanha de pontos - Santander', 2, 1, '2023-12-31 00:00:00', '2024-02-29 23:59:59', 5),
       ('QUEROALEXA', 'Campanha de pontos - Amazon', 1, 1, '2023-12-31 00:00:00', '2024-02-29 23:59:59',
        6);


INSERT INTO product (code, name, price, points)
VALUES
    ('PRD01', 'Day Off', 0.00, 2000),
    ('PRD02', 'Voucher no iFood 50 Reais', 50.00, 500),
    ('PRD03', 'Amazon Echo Pop', 200.00, 180),
    ('PRD04', 'Saldo Livre no Caju de 100 Reais', 100.00, 300),
    ('PRD05', 'Visita ao escritorio de Ipatinga', 1500.00, 5000),
    ('PRD06', 'Visita ao escritorio de BH', 1500.00, 5000),
    ('PRD07', 'Livro Código de Impacto', 70.00, 140),
    ('PRD08', 'Cesta de Café da Manhã', 50.00, 250),
    ('PRD09', 'Camisa de Uniforme', 20.00, 100),
    ('PRD10', 'Kit Churrasco', 120.00, 400),
    ('PRD11', 'Garrafinha Rethink', 40.00, 280),
    ('PRD12', 'Assinatura de 1 Mes de Amazon Prime', 15.00, 80),
    ('PRD13', 'Mochila Rethink', 40.00, 200),
    ('PRD14', '3 Meses de assinatura Alura', 300.00, 500),
    ('PRD15', 'Um jogo de Nintendo Switch para seu Escritorio', 250.00, 400);

INSERT INTO orders (id, user_cpf, partner_code, token, order_number, order_date, status, total, change_date, points,
                    credit_date)
VALUES ('001_CAJ', '11111111111', 'CAJ', '8bd0166f843341db8f304e7fe37cb9b4', '155', '2023-12-27 16:00:00',
        'ORDER_CONFIRMED', 325.7, '2023-12-28 16:04:13', 651.4, '2024-12-01 01:00:00'),
       ('001_IFD', '99999999999', 'IFD', '1107e0b839d045838c8257c15f26d734', '168', '2023-12-27 16:00:00',
        'ORDER_CONFIRMED', 951.7, '2023-12-28 16:00:13', 1903.4, '2024-12-01 01:00:00'),
       ('002_IFD', '11111111111', 'IFD', 'a1c0b6164ec744b0a31ce88544d77f19', '169', '2023-12-27 16:00:00',
        'ORDER_CONFIRMED', 421.4, '2023-12-28 16:00:13', 842.8, '2024-12-01 01:00:00'),
       ('001_MCD', '33333333333', 'MCD', '0336b3dda28c4f84ac2a32e0add72134', '198', '2023-12-27 16:00:00',
        'ORDER_CONFIRMED', 325.7, '2023-12-28 16:00:13', 651.4, '2024-12-01 01:00:00');

INSERT INTO item (id, order_id, sku, price, quantity)
VALUES (1, '001_CAJ', 'tb6', 22.4, 2),
       (1, '001_IFD', '4rw', 7.9, 3),
       (1, '002_IFD', '4rw', 7.9, 2),
       (1, '001_MCD', 'tb6', 22.4, 2),
       (2, '001_CAJ', 'fg6', 80.9, 1),
       (2, '001_IFD', 'a8d', 5.6, 5),
       (2, '002_IFD', 'a8d', 5.6, 1),
       (2, '001_MCD', 'fg6', 80.9, 1),
       (3, '001_CAJ', '67j', 100, 2),
       (3, '001_IFD', 'aa2', 100, 9),
       (3, '002_IFD', 'aa2', 100, 4),
       (3, '001_MCD', '67j', 100, 2);