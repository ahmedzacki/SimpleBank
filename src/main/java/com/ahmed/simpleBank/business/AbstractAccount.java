package com.ahmed.simpleBank.business;

import java.math.BigDecimal;
import java.sql.Timestamp;

public abstract class AbstractAccount implements Account {
    private final int accountId;
    private final int userId;
    private final AccountTypeEnum accountType;
    private final Timestamp createdAt;

    // Constructor
    public AbstractAccount(int accountId, int userId, AccountTypeEnum accountType, Timestamp createdAt) {
        this.accountId = accountId;
        this.userId = userId;
        this.accountType = accountType;
        this.createdAt = createdAt;
    }

    @Override
    public int getAccountId() {
        return accountId;
    }

    @Override
    public int getUserId() {
        return userId;
    }

    @Override
    public AccountTypeEnum getAccountType() {
        return accountType;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public abstract BigDecimal getBalance();  // Abstract method, to be implemented by subclasses

    public abstract void deposit(BigDecimal amount);  // Abstract method, to be implemented by subclasses

    public abstract void withdraw(BigDecimal amount);  // Abstract method, to be implemented by subclasses
}
