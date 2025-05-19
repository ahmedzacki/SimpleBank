package com.ahmed.simpleBank.business;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

/**
 * Implementation of the Account interface.
 * Represents a bank account with balance, user ID, and account type.
 */

public class Account {

    private UUID accountId;
    private UUID userId;
    private BigDecimal balance;
    private AccountTypeEnum accountType;
    private Timestamp createdAt;

    public Account(UUID accountId, UUID userId, AccountTypeEnum accountType, BigDecimal balance, Timestamp createdAt) {
        this.accountId = accountId;
        this.userId = userId;
        this.accountType = accountType;
        this.balance = balance;
        this.createdAt = createdAt;
    }

    public Account(UUID accountId, UUID userId, AccountTypeEnum accountType) {
        this.accountId = accountId;
        this.userId = userId;
        this.accountType = accountType;
        this.balance = BigDecimal.ZERO;
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    public Account(){}

    public UUID getAccountId() {
        return accountId;
    }

    public UUID getUserId() {
        return userId;
    }

    public AccountTypeEnum getAccountType() {
        return accountType;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    // These methods should not contain business logic. They are just getters.
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }


}
