-- Insert test customers
INSERT INTO customer (id, first_name, last_name, email) VALUES
    (10001, 'John', 'Doe', 'john.doe@example.com'),
    (10002, 'Jane', 'Smith', 'jane.smith@example.com'),
    (10003, 'Bob', 'Johnson', 'bob.johnson@example.com');

-- Insert test vouchers (valid for 3 months from current date)
INSERT INTO voucher (id, code, amount, valid_from, valid_until, customer_id, used_at) VALUES
    (10001, 'TEST001', 100.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP + INTERVAL '3 months', 10001, NULL),
    (10002, 'TEST002', 50.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP + INTERVAL '3 months', 10002, NULL),
    (10003, 'TEST003', 75.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP + INTERVAL '3 months', 10003, NULL);
