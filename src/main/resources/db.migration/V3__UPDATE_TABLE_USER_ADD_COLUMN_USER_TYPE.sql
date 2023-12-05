ALTER TABLE user
    ADD COLUMN user_type ENUM('STANDARD', 'ADMIN') DEFAULT 'standard';