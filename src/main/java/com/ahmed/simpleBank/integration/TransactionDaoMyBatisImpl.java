package com.ahmed.simpleBank.integration;

import com.ahmed.simpleBank.business.Transaction;
import com.ahmed.simpleBank.business.TransactionTypeEnum;
import com.ahmed.simpleBank.integration.mapper.TransactionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository("transactionDao")
public class TransactionDaoMyBatisImpl implements TransactionDao {
    
    private final TransactionMapper mapper;
    
    @Autowired
    public TransactionDaoMyBatisImpl(TransactionMapper mapper) {
        this.mapper = mapper;
    }
    
    @Override
    public int createTransaction(Transaction transaction) {
        // Generate UUID if not provided
        if (transaction.getTransactionId() == null) {
            transaction.setTransactionId(UUID.randomUUID());
        }
        return mapper.queryForInsertTransaction(transaction);
    }
    
    @Override
    public Transaction getTransactionById(UUID transactionId) {
        return mapper.queryForGetTransactionById(transactionId);
    }
    
    @Override
    public List<Transaction> getTransactionsByAccountId(UUID accountId) {
        return mapper.queryForGetTransactionsByAccountId(accountId);
    }
    
    @Override
    public List<Transaction> getTransactionsByType(TransactionTypeEnum type) {
        return mapper.queryForGetTransactionsByType(type.toString());
    }
    
    @Override
    public List<Transaction> getTransactionsByAccountIdAndType(UUID accountId, TransactionTypeEnum type) {
        return mapper.queryForGetTransactionsByAccountIdAndType(accountId, type.toString());
    }
}
