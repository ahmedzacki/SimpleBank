package com.ahmed.simpleBank.business;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

public class CheckingAccount extends AbstractAccount {
    private BigDecimal balance;
    private AccountTypeEnum accountType;

    public CheckingAccount() {}

    public CheckingAccount(int accountId, UUID userId, BigDecimal balance, Timestamp createdAt) {
        super(accountId, userId, createdAt);
        this.balance = balance;
        this.accountType = AccountTypeEnum.CHECKING;
    }

    public CheckingAccount(int accountId, UUID userId, Timestamp createdAt) {
        super(accountId, userId, createdAt);
        this.accountType = AccountTypeEnum.CHECKING;
    }

    @Override
    public BigDecimal getBalance() {
        return balance;
    }

    public AccountTypeEnum getAccountType() {
        return accountType;
    }

    @Override
    public void deposit(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) > 0) {
            this.balance = this.balance.add(amount);
        } else {
            throw new IllegalArgumentException("Deposit amount must be positive.");
        }
    }

    @Override
    public void withdraw(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) > 0 && amount.compareTo(balance) <= 0) {
            this.balance = this.balance.subtract(amount);
        } else {
            throw new IllegalArgumentException("Insufficient funds or invalid withdrawal amount.");
        }
    }

    @Override
    public String toString() {
        return "CheckingAccount{" +
                "accountId=" + getAccountId() +
                ", userId=" + getUserId() +
                ", balance=" + getBalance() +
                ", createdAt=" + getCreatedAt() +
                ", accountType=" + getAccountType().getAccountType() +
                '}';
    }
}
