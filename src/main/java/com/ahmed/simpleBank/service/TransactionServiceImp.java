package com.ahmed.simpleBank.service;

import com.ahmed.simpleBank.business.Transaction;
import com.ahmed.simpleBank.business.TransactionTypeEnum;
import com.ahmed.simpleBank.exception.AccountNotFoundException;
import com.ahmed.simpleBank.exception.DatabaseException;
import com.ahmed.simpleBank.exception.InvalidTransactionException;
import com.ahmed.simpleBank.exception.InvalidUserInputException;
import com.ahmed.simpleBank.controller.DatabaseRequestResult;
import com.ahmed.simpleBank.integration.AccountDao;
import com.ahmed.simpleBank.integration.TransactionDao;
import com.ahmed.simpleBank.utils.Validation;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static com.ahmed.simpleBank.utils.Validation.validateTransaction;

@Service
public class TransactionServiceImp implements TransactionService {
    
    @Autowired
    private Logger logger;
    
    private final TransactionDao dao;
    private final AccountDao accountDao;
    
    @Autowired
    public TransactionServiceImp(TransactionDao transactionDao, AccountDao accountDao) {
        this.dao = transactionDao;
        this.accountDao = accountDao;
    }
    
    /**
     * Record a deposit transaction
     */
    @Override
    @Transactional
    public DatabaseRequestResult recordDeposit(UUID toAccountId, BigDecimal amount) {
        Transaction transaction = new Transaction();
        transaction.setTransactionId(UUID.randomUUID());
        transaction.setToAccountId(toAccountId);
        transaction.setAmount(amount);
        transaction.setTransactionType(TransactionTypeEnum.DEPOSIT);
        transaction.setTransactionDate(Timestamp.from(Instant.now()));

        Validation.validateAccountId(toAccountId, accountDao);
        Validation.validateAmount(amount);
        Validation.validateTransaction(transaction);
        
        try {
            int rowsAffected = dao.createTransaction(transaction);
            logger.info("Recorded deposit of {} to account {}", amount, toAccountId);
            return new DatabaseRequestResult(rowsAffected);
        } catch (DataAccessException e) {
            logger.error("Database error while recording deposit transaction", e);
            throw new DatabaseException("Database error: ", e);
        } catch (Exception e) {
            logger.error("Failed to record deposit transaction", e);
            throw new RuntimeException("Failed to record transaction", e);
        }
    }
    
    /**
     * Record a withdrawal transaction
     */
    @Override
    @Transactional
    public DatabaseRequestResult recordWithdrawal(UUID fromAccountId, BigDecimal amount) {
        Transaction transaction = new Transaction();
        transaction.setTransactionId(UUID.randomUUID());
        transaction.setFromAccountId(fromAccountId);
        transaction.setAmount(amount);
        transaction.setTransactionType(TransactionTypeEnum.WITHDRAWAL);
        transaction.setTransactionDate(Timestamp.from(Instant.now()));

        Validation.validateAccountId(fromAccountId, accountDao);
        Validation.validateAmount(amount);
        Validation.validateTransaction(transaction);
        
        
        try {
            int rowsAffected = dao.createTransaction(transaction);
            logger.info("Recorded withdrawal of {} from account {}", amount, fromAccountId);
            return new DatabaseRequestResult(rowsAffected);
        } catch (DataAccessException e) {
            logger.error("Database error while recording withdrawal transaction", e);
            throw new DatabaseException("Database error: ", e);
        } catch (Exception e) {
            logger.error("Failed to record withdrawal transaction", e);
            throw new RuntimeException("Failed to record transaction", e);
        }
    }
    
    /**
     * Record a transfer transaction
     */
    @Override
    @Transactional
    public DatabaseRequestResult recordTransfer(UUID fromAccountId, UUID toAccountId, BigDecimal amount) {
        Transaction transaction = new Transaction();
        transaction.setTransactionId(UUID.randomUUID());
        transaction.setFromAccountId(fromAccountId);
        transaction.setToAccountId(toAccountId);
        transaction.setAmount(amount);
        transaction.setTransactionType(TransactionTypeEnum.TRANSFER);
        transaction.setTransactionDate(Timestamp.from(Instant.now()));

        Validation.validateAccountId(fromAccountId, accountDao);
        Validation.validateAccountId(toAccountId, accountDao);
        Validation.validateAmount(amount);
        Validation.validateTransaction(transaction);
        
        try {
            int rowsAffected = dao.createTransaction(transaction);
            logger.info("Recorded transfer of {} from account {} to account {}", 
                    amount, fromAccountId, toAccountId);
            return new DatabaseRequestResult(rowsAffected);
        } catch (DataAccessException e) {
            logger.error("Database error while recording transfer transaction", e);
            throw new DatabaseException("Database error: ", e);
        } catch (Exception e) {
            logger.error("Failed to record transfer transaction", e);
            throw new RuntimeException("Failed to record transaction", e);
        }
    }
    
    /**
     * Record an interest payment transaction
     */
    @Override
    @Transactional
    public DatabaseRequestResult recordInterestPayment(UUID toAccountId, BigDecimal amount) {
        Transaction transaction = new Transaction();
        transaction.setTransactionId(UUID.randomUUID());
        transaction.setToAccountId(toAccountId);
        transaction.setAmount(amount);
        transaction.setTransactionType(TransactionTypeEnum.INTEREST);
        transaction.setTransactionDate(Timestamp.from(Instant.now()));

        Validation.validateAccountId(toAccountId, accountDao);
        Validation.validateAmount(amount);
        Validation.validateTransaction(transaction);
        
        try {
            int rowsAffected = dao.createTransaction(transaction);
            logger.info("Recorded interest payment of {} to account {}", amount, toAccountId);
            return new DatabaseRequestResult(rowsAffected);
        } catch (DataAccessException e) {
            logger.error("Database error while recording interest payment transaction", e);
            throw new DatabaseException("Database error: ", e);
        } catch (Exception e) {
            logger.error("Failed to record interest payment transaction", e);
            throw new RuntimeException("Failed to record transaction", e);
        }
    }
    
    /**
     * Record a fee transaction
     */
    @Override
    @Transactional
    public DatabaseRequestResult recordFee(UUID fromAccountId, BigDecimal amount) {
        Transaction transaction = new Transaction();
        transaction.setTransactionId(UUID.randomUUID());
        transaction.setFromAccountId(fromAccountId);
        transaction.setAmount(amount);
        transaction.setTransactionType(TransactionTypeEnum.FEE);
        transaction.setTransactionDate(Timestamp.from(Instant.now()));

        Validation.validateAccountId(fromAccountId, accountDao);
        Validation.validateAmount(amount);
        Validation.validateTransaction(transaction);
        
        try {
            int rowsAffected = dao.createTransaction(transaction);
            logger.info("Recorded fee of {} from account {}", amount, fromAccountId);
            return new DatabaseRequestResult(rowsAffected);
        } catch (DataAccessException e) {
            logger.error("Database error while recording fee transaction", e);
            throw new DatabaseException("Database error: ", e);
        } catch (Exception e) {
            logger.error("Failed to record fee transaction", e);
            throw new RuntimeException("Failed to record transaction", e);
        }
    }
    
    /**
     * Get transaction by ID
     */
    @Override
    public Transaction getTransactionById(UUID transactionId) {
        try {
            return dao.getTransactionById(transactionId);
        } catch (DataAccessException e) {
            logger.error("Database error while retrieving transaction by ID", e);
            throw new DatabaseException("Database error: ", e);
        } catch (Exception e) {
            logger.error("Failed to retrieve transaction by ID", e);
            throw new RuntimeException("Failed to retrieve transaction", e);
        }
    }
    
    /**
     * Get all transactions for an account
     */
    @Override
    public List<Transaction> getTransactionsByAccountId(UUID accountId) {
        Validation.validateAccountId(accountId, accountDao);
        try {
            return dao.getTransactionsByAccountId(accountId);
        } catch (DataAccessException e) {
            logger.error("Database error while retrieving transactions for account", e);
            throw new DatabaseException("Database error: ", e);
        } catch (Exception e) {
            logger.error("Failed to retrieve transactions for account", e);
            throw new RuntimeException("Failed to retrieve transactions", e);
        }
    }
    
    /**
     * Get transactions by type
     */
    @Override
    public List<Transaction> getTransactionsByType(TransactionTypeEnum type) {
        Validation.validateTransactionType(type);
        try {
            return dao.getTransactionsByType(type);
        } catch (DataAccessException e) {
            logger.error("Database error while retrieving transactions of type", e);
            throw new DatabaseException("Database error: ", e);
        } catch (Exception e) {
            logger.error("Failed to retrieve transactions of type", e);
            throw new RuntimeException("Failed to retrieve transactions", e);
        }
    }
    
    /**
     * Get transactions for an account by type
     */
    @Override
    public List<Transaction> getTransactionsByAccountIdAndType(UUID accountId, TransactionTypeEnum type) {
        Validation.validateAccountId(accountId, accountDao);
        Validation.validateTransactionType(type);
        try {
            return dao.getTransactionsByAccountIdAndType(accountId, type);
        } catch (DataAccessException e) {
            logger.error("Database error while retrieving transactions for account and type", e);
            throw new DatabaseException("Database error: ", e);
        } catch (Exception e) {
            logger.error("Failed to retrieve transactions for account and type", e);
            throw new RuntimeException("Failed to retrieve transactions", e);
        }
    }

}
