-- ==============================================
-- MySQL Init Script for FPM-2025 Libraries
-- Tạo databases cần thiết cho microservices
-- ==============================================

-- Tạo databases
CREATE DATABASE IF NOT EXISTS user_auth_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS wallet_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS reporting_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Grant quyền cho user 'fpm_user'
GRANT ALL PRIVILEGES ON user_auth_db.* TO 'fpm_user'@'%';
GRANT ALL PRIVILEGES ON wallet_db.* TO 'fpm_user'@'%';
GRANT ALL PRIVILEGES ON reporting_db.* TO 'fpm_user'@'%';
FLUSH PRIVILEGES;

-- Create audit log table in default db
CREATE TABLE IF NOT EXISTS audit_log (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    entity_type VARCHAR(255),
    entity_id BIGINT,
    action VARCHAR(50),
    old_values TEXT,
    new_values TEXT,
    created_by VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
