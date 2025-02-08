-- Customer table and sequence
CREATE SEQUENCE customer_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE customer (
    id BIGINT PRIMARY KEY DEFAULT nextval('customer_seq'),
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255)
);

-- Voucher table and sequence
CREATE SEQUENCE voucher_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE voucher (
    id BIGINT PRIMARY KEY DEFAULT nextval('voucher_seq'),
    code VARCHAR(255) NOT NULL UNIQUE,
    amount DECIMAL(19,2) NOT NULL,
    valid_from TIMESTAMP NOT NULL,
    valid_until TIMESTAMP NOT NULL,
    customer_id BIGINT REFERENCES customer(id),
    used_at TIMESTAMP
);
