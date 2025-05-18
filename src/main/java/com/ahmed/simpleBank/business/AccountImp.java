package com.ahmed.simpleBank.business;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

/**
 * Implementation of the Account interface.
 * Represents a bank account with balance, user ID, and account type.
 */
public class AccountImp implements Account {

    private final UUID accountId;
    private final UUID userId;
    private BigDecimal balance;
    private final AccountTypeEnum accountType;
    private final Timestamp createdAt;

    public AccountImp(UUID accountId, UUID userId, AccountTypeEnum accountType, BigDecimal balance, Timestamp createdAt) {
        this.accountId = accountId;
        this.userId = userId;
        this.accountType = accountType;
        this.balance = balance;
        this.createdAt = createdAt;
    }

    public AccountImp(UUID accountId, UUID userId, AccountTypeEnum accountType) {
        this.accountId = accountId;
        this.userId = userId;
        this.accountType = accountType;
        this.balance = BigDecimal.ZERO;
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    @Override
    public UUID getAccountId() {
        return accountId;
    }

    @Override
    public UUID getUserId() {
        return userId;
    }

    @Override
    public AccountTypeEnum getAccountType() {
        return accountType;
    }

    @Override
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountImp that = (AccountImp) o;
        return Objects.equals(accountId, that.accountId) && Objects.equals(userId, that.userId) && Objects.equals(balance, that.balance) && accountType == that.accountType && Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, userId, balance, accountType, createdAt);
    }

    @Override
    public String toString() {
        return "AccountImp{" +
                "accountId=" + accountId +
                ", userId=" + userId +
                ", balance=" + balance +
                ", accountType=" + accountType +
                ", createdAt=" + createdAt +
                '}';
    }
}
