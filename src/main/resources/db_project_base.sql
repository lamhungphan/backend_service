CREATE DATABASE db_project_base;

USE db_project_base;

CREATE TABLE tbl_user (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    date_of_birth DATE,
    gender VARCHAR(10),
    phone VARCHAR(20),
    email VARCHAR(100) UNIQUE,
    username VARCHAR(50) UNIQUE NOT NULL,
    password TEXT NOT NULL,
    status VARCHAR(20),
    type VARCHAR(20),
    created_at TIMESTAMP DEFAULT now(),
    updated_at TIMESTAMP DEFAULT now()
);

CREATE TABLE tbl_address (
    id SERIAL PRIMARY KEY,
    apartment_number VARCHAR(20),
    floor VARCHAR(10),
    building VARCHAR(50),
    street_number VARCHAR(20),
    street VARCHAR(100),
    city VARCHAR(50),
    country VARCHAR(50),
    address_type VARCHAR(20),
    user_id INT REFERENCES tbl_user(id) ON DELETE CASCADE,
    created_at TIMESTAMP DEFAULT now(),
    updated_at TIMESTAMP DEFAULT now()
);

CREATE TABLE tbl_role (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE tbl_group (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    role_id INT REFERENCES tbl_role(id) ON DELETE SET NULL
);

CREATE TABLE tbl_permission (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE tbl_role_has_permission (
    id SERIAL PRIMARY KEY,
    role_id INT REFERENCES tbl_role(id) ON DELETE CASCADE,
    permission_id INT REFERENCES tbl_permission(id) ON DELETE CASCADE
);

CREATE TABLE tbl_user_has_group (
    id SERIAL PRIMARY KEY,
    user_id INT REFERENCES tbl_user(id) ON DELETE CASCADE,
    group_id INT REFERENCES tbl_group(id) ON DELETE CASCADE
);

CREATE TABLE tbl_user_has_role (
    id SERIAL PRIMARY KEY,
    user_id INT REFERENCES tbl_user(id) ON DELETE CASCADE,
    role_id INT REFERENCES tbl_role(id) ON DELETE CASCADE
);

CREATE TABLE tbl_token (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL REFERENCES tbl_user(username) ON DELETE CASCADE,
    access_token TEXT NOT NULL,
    refresh_token TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT now(),
    updated_at TIMESTAMP DEFAULT now()
);

-- Trigger cập nhật updated_at
CREATE OR REPLACE FUNCTION update_timestamp()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = now();
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Gán trigger cho các bảng có updated_at
CREATE TRIGGER trg_update_user
    BEFORE UPDATE ON tbl_user
    FOR EACH ROW EXECUTE FUNCTION update_timestamp();

CREATE TRIGGER trg_update_address
    BEFORE UPDATE ON tbl_address
    FOR EACH ROW EXECUTE FUNCTION update_timestamp();

CREATE TRIGGER trg_update_token
    BEFORE UPDATE ON tbl_token
    FOR EACH ROW EXECUTE FUNCTION update_timestamp();
