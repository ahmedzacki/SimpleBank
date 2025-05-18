package com.ahmed.simpleBank.integration;

import com.ahmed.simpleBank.business.Account;


import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface AccountDao {
    int createAccount(Account account);
    
    Account getAccountById(UUID accountId);
    
    List<Account> getAllAccountsByUserId(UUID userId);
    
    int updateBalance(UUID accountId, BigDecimal balance);
    
    int deleteAccount(UUID accountId);
    
    List<Account> getAccountsByUserIdAndType(UUID userId, String accountType);
    
    Boolean accountExists(UUID userId, String accountType);
} 
