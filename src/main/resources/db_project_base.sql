-- CREATE DATABASE db_project_base;

DROP DATABASE IF EXISTS db_project_base;

DROP TABLE IF EXISTS tbl_user;
CREATE TABLE tbl_user (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    date_of_birth DATE,
    gender VARCHAR(10) CHECK (gender IN ('MALE', 'FEMALE', 'OTHER')),
    phone VARCHAR(15),
    email VARCHAR(255) UNIQUE,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    status VARCHAR(20) CHECK (status IN ('NONE', 'ACTIVE', 'INACTIVE')),
    type VARCHAR(20) CHECK (type IN ('OWNER', 'ADMIN', 'USER')),
    created_at TIMESTAMP DEFAULT now(),
    updated_at TIMESTAMP DEFAULT now()
);

INSERT INTO tbl_user (first_name, last_name, date_of_birth, gender, phone, email, username, password, status, type) VALUES
('John', 'Doe', '1990-05-15', 'MALE', '1234567890', 'john.doe@example.com', 'johndoe', '$2a$10$W4UBH6bph/2D3lOBKoK3K.0o3vhfT6sFQGCRoMhXqHEWX7G82A3Fi', 'ACTIVE', 'USER'),
('Jane', 'Smith', '1992-08-21', 'FEMALE', '0987654321', 'jane.smith@example.com', 'janesmith', '$2a$10$W4UBH6bph/2D3lOBKoK3K.0o3vhfT6sFQGCRoMhXqHEWX7G82A3Fi', 'ACTIVE', 'ADMIN'),
('Alice', 'Johnson', '1985-12-03', 'FEMALE', '1122334455', 'alice.j@example.com', 'alicej', '$2a$10$W4UBH6bph/2D3lOBKoK3K.0o3vhfT6sFQGCRoMhXqHEWX7G82A3Fi', 'INACTIVE', 'USER'),
('Bob', 'Brown', '1998-04-10', 'MALE', '6677889900', 'bob.b@example.com', 'bobb', '$2a$10$W4UBH6bph/2D3lOBKoK3K.0o3vhfT6sFQGCRoMhXqHEWX7G82A3Fi', 'ACTIVE', 'OWNER'),
('Charlie', 'Davis', '1991-11-25', 'OTHER', '5566778899', 'charlie.d@example.com', 'charlied', '$2a$10$W4UBH6bph/2D3lOBKoK3K.0o3vhfT6sFQGCRoMhXqHEWX7G82A3Fi', 'NONE', 'USER'),
('David', 'White', '1987-07-14', 'MALE', '2233445566', 'david.w@example.com', 'davidw', '$2a$10$W4UBH6bph/2D3lOBKoK3K.0o3vhfT6sFQGCRoMhXqHEWX7G82A3Fi', 'ACTIVE', 'ADMIN'),
('Emma', 'Harris', '1994-03-30', 'FEMALE', '3344556677', 'emma.h@example.com', 'emmah', '$2a$10$W4UBH6bph/2D3lOBKoK3K.0o3vhfT6sFQGCRoMhXqHEWX7G82A3Fi', 'INACTIVE', 'USER'),
('Frank', 'Wilson', '1995-06-05', 'MALE', '4455667788', 'frank.w@example.com', 'frankw', '$2a$10$W4UBH6bph/2D3lOBKoK3K.0o3vhfT6sFQGCRoMhXqHEWX7G82A3Fi', 'ACTIVE', 'OWNER'),
('Grace', 'Taylor', '1989-09-17', 'FEMALE', '5566778899', 'grace.t@example.com', 'gracet', '$2a$10$W4UBH6bph/2D3lOBKoK3K.0o3vhfT6sFQGCRoMhXqHEWX7G82A3Fi', 'NONE', 'USER'),
('Hannah', 'Anderson', '1993-01-23', 'FEMALE', '6677889900', 'hannah.a@example.com', 'hanna', '$2a$10$W4UBH6bph/2D3lOBKoK3K.0o3vhfT6sFQGCRoMhXqHEWX7G82A3Fi', 'ACTIVE', 'ADMIN'),
('Ian', 'Thomas', '1986-10-12', 'MALE', '7788990011', 'ian.t@example.com', 'iant', '$2a$10$W4UBH6bph/2D3lOBKoK3K.0o3vhfT6sFQGCRoMhXqHEWX7G82A3Fi', 'INACTIVE', 'USER'),
('Jack', 'Moore', '1997-05-19', 'MALE', '8899001122', 'jack.m@example.com', 'jackm', '$2a$10$W4UBH6bph/2D3lOBKoK3K.0o3vhfT6sFQGCRoMhXqHEWX7G82A3Fi', 'ACTIVE', 'OWNER'),
('Katie', 'Lee', '1990-07-08', 'FEMALE', '9900112233', 'katie.l@example.com', 'katiel', '$2a$10$W4UBH6bph/2D3lOBKoK3K.0o3vhfT6sFQGCRoMhXqHEWX7G82A3Fi', 'NONE', 'USER'),
('Liam', 'Walker', '1992-11-14', 'MALE', '1122334455', 'liam.w@example.com', 'liamw', '$2a$10$W4UBH6bph/2D3lOBKoK3K.0o3vhfT6sFQGCRoMhXqHEWX7G82A3Fi', 'ACTIVE', 'ADMIN'),
('Mia', 'Hall', '1988-02-26', 'FEMALE', '2233445566', 'mia.h@example.com', 'miah', '$2a$10$W4UBH6bph/2D3lOBKoK3K.0o3vhfT6sFQGCRoMhXqHEWX7G82A3Fi', 'INACTIVE', 'USER');

DROP TABLE IF EXISTS  tbl_address;
CREATE TABLE tbl_address (
    id SERIAL PRIMARY KEY,
    apartment_number VARCHAR(20),
    floor VARCHAR(10),
    building VARCHAR(50),
    street_number VARCHAR(20),
    street VARCHAR(100),
    city VARCHAR(50),
    country VARCHAR(50),
    address_type INT, -- 0 = HOME, 1 = WORK
    user_id INT REFERENCES tbl_user(id) ON DELETE CASCADE,
    created_at TIMESTAMP DEFAULT now(),
    updated_at TIMESTAMP DEFAULT now()
);

INSERT INTO tbl_address (apartment_number, floor, building, street_number, street, city, country, address_type, user_id) VALUES
('A1', '1', 'Building A', '123', 'Main Street', 'New York', 'USA', '0', 1),
('B2', '2', 'Building B', '456', 'Second Street', 'Los Angeles', 'USA', '1', 2),
('C3', '3', 'Building C', '789', 'Third Avenue', 'Chicago', 'USA', '0', 3),
('D4', '4', 'Building D', '101', 'Fourth Blvd', 'Houston', 'USA', '1', 4),
('E5', '5', 'Building E', '202', 'Fifth Road', 'San Francisco', 'USA', '1', 5),
('F6', '6', 'Building F', '303', 'Sixth Lane', 'Boston', 'USA', '0', 6),
('G7', '7', 'Building G', '404', 'Seventh Circle', 'Miami', 'USA', '1', 7),
('H8', '8', 'Building H', '505', 'Eighth Square', 'Seattle', 'USA', '0', 8),
('I9', '9', 'Building I', '606', 'Ninth Place', 'Denver', 'USA', '1', 9),
('J10', '10', 'Building J', '707', 'Tenth Alley', 'Austin', 'USA', '0', 10);

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

CREATE TABLE tbl_product (
    id SERIAL PRIMARY KEY,
    price FLOAT NOT NULL,
    created_at TIMESTAMP DEFAULT now(),
    updated_at TIMESTAMP DEFAULT now(),
    description VARCHAR(50) NOT NULL,
    name VARCHAR(50) NOT NULL,
    user_id INT NOT NULL
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
