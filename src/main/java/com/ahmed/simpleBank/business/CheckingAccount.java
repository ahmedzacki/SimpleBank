package com.ahmed.simpleBank.business;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class CheckingAccount extends AbstractAccount {
    private BigDecimal balance;

    public CheckingAccount(int accountId, int userId, BigDecimal balance, Timestamp createdAt) {
        super(accountId, userId, AccountTypeEnum.CHECKING, createdAt); // Pass the AccountTypeEnum value
        this.balance = balance;
    }

    @Override
    public BigDecimal getBalance() {
        return balance;
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
                ", accountType=" + getAccountType().getAccountType() + // Display the account type as a string
                '}';
    }
}
