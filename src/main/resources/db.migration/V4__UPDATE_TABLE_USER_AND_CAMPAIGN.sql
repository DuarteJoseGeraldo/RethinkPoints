ALTER TABLE user
    ADD COLUMN status ENUM('ACTIVE', 'INACTIVE');

ALTER TABLE campaign
    ADD CONSTRAINT uk_campaign_id UNIQUE (id_campaign);

