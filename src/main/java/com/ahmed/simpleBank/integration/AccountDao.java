package com.ahmed.simpleBank.integration;

import com.ahmed.simpleBank.business.Account;


import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface AccountDao {
    int createAccount(Account account);
    
    Account getAccountById(int accountId);
    
    List<Account> getAccountsByUserId(UUID userId);
    
    int updateBalance(int accountId, BigDecimal balance);
    
    int deleteAccount(int accountId);
    
    Account getAccountByUserIdAndType(UUID userId, String accountType);
    
    Boolean accountExists(UUID userId, String accountType);
} 
