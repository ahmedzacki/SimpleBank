DROP DATABASE IF EXISTS SimpleBankDB;

CREATE DATABASE SimpleBankDB;
USE SimpleBankDB;


CREATE TABLE users (
       userId        CHAR(36)     PRIMARY KEY,
       firstName     VARCHAR(255) NOT NULL,
       lastName      VARCHAR(255) NOT NULL,
       email         VARCHAR(255) NOT NULL UNIQUE,
       username      VARCHAR(255) NOT NULL UNIQUE,
       password      VARCHAR(255) NOT NULL,
       userRole      VARCHAR(50)  DEFAULT 'USER',
       isAccountNonExpired BOOLEAN DEFAULT TRUE,
       isAccountNonLocked BOOLEAN DEFAULT TRUE,
       isCredentialsNonExpired BOOLEAN DEFAULT TRUE,
       isEnabled BOOLEAN DEFAULT TRUE,
       createdAt     DATETIME     DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE accounts (
      accountId   CHAR(36)       PRIMARY KEY,
      userId      CHAR(36)       NOT NULL,
      accountType VARCHAR(20)    NOT NULL,
      balance     DECIMAL(19,2)  NOT NULL DEFAULT 0.00,
      createdAt   DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP,
      FOREIGN KEY (userId) REFERENCES users(userId)
);

CREATE TABLE transactions (
      transaction_id CHAR(36) NOT NULL PRIMARY KEY,
      from_account_id CHAR(36) NULL,
      to_account_id CHAR(36) NULL,
      amount DECIMAL(19, 4) NOT NULL,
      transaction_type VARCHAR(20) NOT NULL,
      transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

      FOREIGN KEY (from_account_id) REFERENCES accounts(accountId) ON DELETE SET NULL,
      FOREIGN KEY (to_account_id) REFERENCES accounts(accountId) ON DELETE SET NULL
);

-- Indexes for better query performance
CREATE INDEX idx_transactions_from_account ON transactions(from_account_id);
CREATE INDEX idx_transactions_to_account ON transactions(to_account_id);
CREATE INDEX idx_transactions_date ON transactions(transaction_date);

COMMIT;