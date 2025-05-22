package com.ahmed.simpleBank.integration;

import com.ahmed.simpleBank.business.Transaction;
import com.ahmed.simpleBank.business.TransactionTypeEnum;

import java.util.List;
import java.util.UUID;

public interface TransactionDao {
    // Create a new transaction
    int createTransaction(Transaction transaction);
    
    // Get transaction by ID
    Transaction getTransactionById(UUID transactionId);
    
    // Get all transactions for an account (either as sender or receiver)
    List<Transaction> getTransactionsByAccountId(UUID accountId);
    
    // Get transactions by type
    List<Transaction> getTransactionsByType(TransactionTypeEnum type);
    
    // Get transactions for a specific account by type
    List<Transaction> getTransactionsByAccountIdAndType(UUID accountId, TransactionTypeEnum type);
}
