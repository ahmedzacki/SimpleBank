package com.ahmed.simpleBank.service;

import com.ahmed.simpleBank.business.Account;
import com.ahmed.simpleBank.business.AccountTypeEnum;
import com.ahmed.simpleBank.controller.DatabaseRequestResult;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface AccountService {

    // Account retrieval
    Account findAccountById(UUID accountId);
    List<Account> findAllAccountsByUserId(UUID userId);
    List<Account> findAccountsByUserIdAndType(UUID userId, AccountTypeEnum accountType);
    
    // Account operations
    DatabaseRequestResult deposit(UUID accountId, BigDecimal amount);
    DatabaseRequestResult withdraw(UUID accountId, BigDecimal amount);
    void transfer(UUID fromAccountId, UUID toAccountId, BigDecimal amount);
    BigDecimal getBalance(UUID accountId);
    
    // Account management
    DatabaseRequestResult deleteAccount(UUID accountId); 
}
