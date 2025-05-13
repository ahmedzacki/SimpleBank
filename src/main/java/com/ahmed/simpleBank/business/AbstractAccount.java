package com.ahmed.simpleBank.business;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

public abstract class AbstractAccount implements Account {
    private Integer accountId;
    private UUID userId;
    private Timestamp createdAt;

    /* MyBatis needs to be able to call a no-args constructor on whatever concrete class it instantiates, and then set properties via setters */
    protected AbstractAccount() { }

    // Constructor
    public AbstractAccount(int accountId, UUID userId, Timestamp createdAt) {
        this.accountId = accountId;
        this.userId = userId;
        this.createdAt = createdAt;
    }

    @Override
    public int getAccountId() {
        return accountId;
    }

    @Override
    public UUID getUserId() {
        return userId;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public abstract BigDecimal getBalance();  // Abstract method, to be implemented by subclasses

    public abstract void deposit(BigDecimal amount);  // Abstract method, to be implemented by subclasses

    public abstract void withdraw(BigDecimal amount);  // Abstract method, to be implemented by subclasses
}
