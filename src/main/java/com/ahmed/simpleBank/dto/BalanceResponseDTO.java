package com.ahmed.simpleBank.dto;

import java.math.BigDecimal;

public class BalanceResponseDTO {
    private BigDecimal balance;

    public BalanceResponseDTO() {
    }

    public BalanceResponseDTO(BigDecimal amount) {
        this.balance = amount;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "BalanceResponseDTO{" +
                "balance=" + balance +
                '}';
    }
}