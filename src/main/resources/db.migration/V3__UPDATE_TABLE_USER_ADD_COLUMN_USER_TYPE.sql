ALTER TABLE user
    ADD COLUMN user_type ENUM('standard', 'admin') DEFAULT 'standard';