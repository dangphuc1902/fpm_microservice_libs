-- PostgreSQL Initialization Script for FPM Development

-- Create application databases
CREATE DATABASE fpm_wallet OWNER fpm_user;
CREATE DATABASE fpm_transaction OWNER fpm_user;
CREATE DATABASE fpm_category OWNER fpm_user;

-- Grant privileges
GRANT ALL PRIVILEGES ON DATABASE fpm_wallet TO fpm_user;
GRANT ALL PRIVILEGES ON DATABASE fpm_transaction TO fpm_user;
GRANT ALL PRIVILEGES ON DATABASE fpm_category TO fpm_user;

-- Connect to fpm_dev and create schemas
\c fpm_dev

CREATE SCHEMA IF NOT EXISTS public;
CREATE SCHEMA IF NOT EXISTS wallet;
CREATE SCHEMA IF NOT EXISTS transaction;
CREATE SCHEMA IF NOT EXISTS category;

GRANT ALL PRIVILEGES ON SCHEMA public TO fpm_user;
GRANT ALL PRIVILEGES ON SCHEMA wallet TO fpm_user;
GRANT ALL PRIVILEGES ON SCHEMA transaction TO fpm_user;
GRANT ALL PRIVILEGES ON SCHEMA category TO fpm_user;

-- Create audit log table
CREATE TABLE IF NOT EXISTS audit_log (
    id BIGSERIAL PRIMARY KEY,
    entity_type VARCHAR(255),
    entity_id BIGINT,
    action VARCHAR(50),
    old_values TEXT,
    new_values TEXT,
    created_by VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

GRANT ALL PRIVILEGES ON audit_log TO fpm_user;

-- Display connection info
\echo '=========================================='
\echo 'PostgreSQL Development Setup Complete'
\echo '=========================================='
\echo ''
\echo 'Databases created:'
\echo '  - fpm_dev (default)'
\echo '  - fpm_wallet'
\echo '  - fpm_transaction'
\echo '  - fpm_category'
\echo ''
\echo 'Connection Details:'
\echo '  Host: postgres'
\echo '  Port: 5432'
\echo '  User: fpm_user'
\echo '  Password: fpm_password'
\echo ''
