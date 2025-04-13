DELETE FROM USERS;
DELETE FROM ACCOUNTS;
DELETE FROM TRANSACTIONS;

INSERT INTO user (first_name, last_name, email, password_hash)
VALUES
    ('John', 'Doe', 'john.doe@example.com', 'hashed_password_1'),
    ('Jane', 'Smith', 'jane.smith@example.com', 'hashed_password_2');


INSERT INTO account (user_id, account_type, balance)
VALUES
    (1, 'Checking', 1000.00),
    (1, 'Savings', 5000.00),
    (2, 'Checking', 2000.00);


-- A deposit to John Doe's checking account
INSERT INTO transaction (from_account_id, to_account_id, amount, transaction_type)
VALUES (NULL, 1, 500.00, 'Deposit');

-- A withdrawal from Jane Smith's checking account
INSERT INTO transaction (from_account_id, to_account_id, amount, transaction_type)
VALUES (2, NULL, 300.00, 'Withdrawal');

-- A transfer between John Doe's accounts
INSERT INTO transaction (from_account_id, to_account_id, amount, transaction_type)
VALUES (1, 2, 200.00, 'Transfer');
