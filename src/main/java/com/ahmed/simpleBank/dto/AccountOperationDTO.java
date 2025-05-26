package com.ahmed.simpleBank.dto;

import java.math.BigDecimal;

public class AccountOperationDTO {
    private BigDecimal amount;

    public AccountOperationDTO() {
    }

    public AccountOperationDTO(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "AccountOperationDTO{" +
                "amount=" + amount +
                '}';
    }
}