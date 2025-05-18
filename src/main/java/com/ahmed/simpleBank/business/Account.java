package com.ahmed.simpleBank.business;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Interface representing the common behavior of a bank account.
 */
public interface Account {
    /**
     * Get the account ID.
     * @return the account ID
     */
    UUID getAccountId();

    /**
     * Get the user ID associated with the account.
     * @return the user ID
     */
    UUID getUserId();

    /**
     * Get the account type (Checking/Savings).
     * @return the account type
     */
    AccountTypeEnum getAccountType();

    /**
     * Get the current balance of the account.
     * @return the account balance
     */
    BigDecimal getBalance();

}
