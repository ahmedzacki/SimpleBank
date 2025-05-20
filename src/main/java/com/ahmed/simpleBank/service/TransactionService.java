package com.ahmed.simpleBank.service;

import com.ahmed.simpleBank.business.Transaction;
import com.ahmed.simpleBank.business.TransactionTypeEnum;
import com.ahmed.simpleBank.controller.DatabaseRequestResult;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface TransactionService {
    
    // Record different types of transactions
    DatabaseRequestResult recordDeposit(UUID toAccountId, BigDecimal amount);
    DatabaseRequestResult recordWithdrawal(UUID fromAccountId, BigDecimal amount);
    DatabaseRequestResult recordTransfer(UUID fromAccountId, UUID toAccountId, BigDecimal amount);
    DatabaseRequestResult recordInterestPayment(UUID toAccountId, BigDecimal amount);
    DatabaseRequestResult recordFee(UUID fromAccountId, BigDecimal amount);
    
    // Query transactions
    Transaction getTransactionById(UUID transactionId);
    List<Transaction> getTransactionsByAccountId(UUID accountId);
    List<Transaction> getTransactionsByType(TransactionTypeEnum type);
    List<Transaction> getTransactionsByAccountIdAndType(UUID accountId, TransactionTypeEnum type);
}
