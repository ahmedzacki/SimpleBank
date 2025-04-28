DROP DATABASE SimpleBankDB;

CREATE DATABASE SimpleBankDB;
USE SimpleBankDB;


CREATE TABLE user (
      user_id INT AUTO_INCREMENT PRIMARY KEY,
      first_name VARCHAR(255) NOT NULL,
      last_name VARCHAR(255) NOT NULL,
      email VARCHAR(255) NOT NULL UNIQUE,
      password_hash VARCHAR(255) NOT NULL,
      role VARCHAR(50) DEFAULT 'USER',
      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE account (
     account_id INT AUTO_INCREMENT PRIMARY KEY,
     user_id INT NOT NULL,
     account_type ENUM('Checking', 'Savings') NOT NULL,
     balance DECIMAL(10, 2) DEFAULT 0.00,
     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
     FOREIGN KEY (user_id) REFERENCES user(user_id)
);


CREATE TABLE transaction (
     transaction_id INT AUTO_INCREMENT PRIMARY KEY,
     from_account_id INT,
     to_account_id INT,
     amount DECIMAL(10, 2) NOT NULL,
     transaction_type ENUM('Deposit', 'Withdrawal', 'Transfer') NOT NULL,
     transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
     FOREIGN KEY (from_account_id) REFERENCES account(account_id),
     FOREIGN KEY (to_account_id) REFERENCES account(account_id)
);

COMMIT;