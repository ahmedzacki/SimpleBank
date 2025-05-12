package com.ahmed.simpleBank.business;

import java.math.BigDecimal;

public interface Account {
    int getAccountId();
    int getUserId();
    AccountTypeEnum getAccountType();
    BigDecimal getBalance();
    void deposit(BigDecimal amount);
    void withdraw(BigDecimal amount);
}
