ALTER TABLE user
    ADD COLUMN is_active BOOLEAN;

ALTER TABLE campaign
    ADD CONSTRAINT uk_campaign_id UNIQUE (id_campaign);

