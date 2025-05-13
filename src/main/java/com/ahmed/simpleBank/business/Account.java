package com.ahmed.simpleBank.business;

import java.math.BigDecimal;
import java.util.UUID;

public interface Account {
    int getAccountId();
    UUID getUserId();
    AccountTypeEnum getAccountType();
    BigDecimal getBalance();
    void deposit(BigDecimal amount);
    void withdraw(BigDecimal amount);
}
