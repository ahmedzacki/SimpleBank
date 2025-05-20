package com.ahmed.simpleBank.service;

import com.ahmed.simpleBank.business.Account;
import com.ahmed.simpleBank.business.AccountTypeEnum;
import com.ahmed.simpleBank.controller.DatabaseRequestResult;
import com.ahmed.simpleBank.exception.AccountNotFoundException;
import com.ahmed.simpleBank.exception.DatabaseException;
import com.ahmed.simpleBank.exception.InsufficientFundsException;
import com.ahmed.simpleBank.exception.InvalidUserInputException;
import com.ahmed.simpleBank.integration.AccountDao;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class AccountServiceImp implements AccountService {

    @Autowired
    private Logger logger;

    private final AccountDao dao;
    private final UserService userService;
    private final TransactionService transactionService;

    public AccountServiceImp(AccountDao dao, UserService userService, TransactionService transactionService) {
        this.dao = dao;
        this.userService = userService;
        this.transactionService = transactionService;
    }

    // *************** Account Creation Methods *************************
    @Override
    @Transactional
    public DatabaseRequestResult createAccount(UUID userId, AccountTypeEnum accountType) {
        try {
            // Verify user exists
            userService.findUserById(userId);
            
            // Generate a UUID that doesn't already exist in the database
            UUID accountId;
            boolean idExists;
            int maxAttempts = 5; // Limit retries to prevent infinite loop
            int attempts = 0;
            
            do {
                accountId = UUID.randomUUID();
                // Check if this ID already exists
                Account existingAccount = dao.getAccountById(accountId);
                idExists = (existingAccount != null);
                attempts++;
                
                if (idExists) {
                    logger.warn("Generated UUID {} already exists, retrying...", accountId);
                }
            } while (idExists && attempts < maxAttempts);
            
            if (idExists) {
                // This is extremely unlikely but we should handle it
                logger.error("Failed to generate unique account ID after {} attempts", maxAttempts);
                throw new DatabaseException("Unable to generate unique account ID");
            }
            
            // Create the account with the unique ID
            Account account = new Account(accountId, userId, accountType);
            int rowsAffected = dao.createAccount(account);
            logger.info("Created new {} account for user {}: {}", accountType, userId, accountId);
            return new DatabaseRequestResult(rowsAffected);
        } catch (DataAccessException dae) {
            logger.error("Database error while creating account for user {}", userId, dae);
            throw new DatabaseException("Error occurred while creating account", dae);
        } catch (Exception e) {
            logger.error("Unexpected error while creating account for user {}", userId, e);
            throw new RuntimeException("An unexpected error occurred while creating account", e);
        }
    }

    
    // *************** Account Retrieval Methods *************************
    @Override
    public Account findAccountById(UUID accountId) {
        try {
            Account account = dao.getAccountById(accountId);
            if (account == null) {
                logger.warn("Account not found with ID: {}", accountId);
                throw new AccountNotFoundException(accountId);
            }
            logger.debug("Retrieved account: {}", account);
            return account;
        } catch (DataAccessException dae) {
            logger.error("Database error while retrieving account {}", accountId, dae);
            throw new DatabaseException("Error occurred while retrieving account", dae);
        } catch (Exception e) {
            logger.error("Unexpected error while retrieving account {}", accountId, e);
            throw new RuntimeException("An unexpected error occurred while retrieving account", e);
        }
    }

    @Override
    public List<Account> findAllAccountsByUserId(UUID userId) {
        try {
            // Verify user exists
            userService.findUserById(userId);
            
            List<Account> accounts = dao.getAllAccountsByUserId(userId);
            logger.debug("Retrieved {} accounts for user {}", accounts.size(), userId);
            return accounts;
        } catch (DataAccessException dae) {
            logger.error("Database error while retrieving accounts for user {}", userId, dae);
            throw new DatabaseException("Error occurred while retrieving accounts", dae);
        } catch (Exception e) {
            logger.error("Unexpected error while retrieving accounts for user {}", userId, e);
            throw new RuntimeException("An unexpected error occurred while retrieving accounts", e);
        }
    }

    @Override
    public List<Account> findAccountsByUserIdAndType(UUID userId, AccountTypeEnum accountType) {
        try {
            // Verify user exists
            userService.findUserById(userId);
            
            List<Account> accounts = dao.getAccountsByUserIdAndType(userId, accountType.getAccountType());

            if (accounts.isEmpty()) {
                logger.warn("Accounts of type {} not found for user {}", accountType, userId);
                throw new AccountNotFoundException("Accounts of type " + accountType + " not found for user");
            }
            
            logger.debug("Retrieved {} accounts for user {}", accounts.size(), userId);
            return accounts;
        } catch (DataAccessException dae) {
            logger.error("Database error while retrieving {} accounts for user {}", accountType, userId, dae);
            throw new DatabaseException("Error occurred while retrieving accounts", dae);
        } catch (Exception e) {
            logger.error("Unexpected error while retrieving {} accounts for user {}", accountType, userId, e);
            throw new RuntimeException("An unexpected error occurred while retrieving accounts", e);
        }
    }

    // *************** Account Operation Methods *************************
    @Override
    @Transactional
    public DatabaseRequestResult deposit(UUID accountId, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            logger.warn("Invalid deposit amount: {}", amount);
            throw new InvalidUserInputException("Deposit amount must be positive");
        }
        
        try {
            Account account = findAccountById(accountId);
            BigDecimal newBalance = account.getBalance().add(amount);
            
            int rowsAffected = dao.updateBalance(accountId, newBalance);
            if (rowsAffected > 0) {
                // Record the deposit transaction in the database
                transactionService.recordDeposit(accountId, amount);
                logger.info("Deposited {} to account {}, new balance: {}", amount, accountId, newBalance);
            } else {
                logger.error("Failed to deposit {} to account {}", amount, accountId);
            }
            
            return new DatabaseRequestResult(rowsAffected);
        } catch (DataAccessException dae) {
            logger.error("Database error while depositing to account {}", accountId, dae);
            throw new DatabaseException("Error occurred while processing deposit", dae);
        } catch (Exception e) {
            logger.error("Unexpected error during deposit to account {}", accountId, e);
            throw new RuntimeException("An unexpected error occurred during deposit", e);
        }
    }

    @Override
    @Transactional
    public DatabaseRequestResult withdraw(UUID accountId, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            logger.warn("Invalid withdrawal amount: {}", amount);
            throw new InvalidUserInputException("Withdrawal amount must be positive");
        }
        
        try {
            Account account = findAccountById(accountId);
            
            if (account.getBalance().compareTo(amount) < 0) {
                logger.warn("Insufficient funds in account {} for withdrawal of {}", accountId, amount);
                throw new InsufficientFundsException("Insufficient funds for withdrawal");
            }
            
            BigDecimal newBalance = account.getBalance().subtract(amount);
            int rowsAffected = dao.updateBalance(accountId, newBalance);
            
            if (rowsAffected > 0) {
                // Record the withdrawal transaction in the database
                transactionService.recordWithdrawal(accountId, amount);
                logger.info("Withdrew {} from account {}, new balance: {}", amount, accountId, newBalance);
            } else {
                logger.error("Failed to withdraw {} from account {}", amount, accountId);
            }
            
            return new DatabaseRequestResult(rowsAffected);
        } catch (DataAccessException dae) {
            logger.error("Database error while withdrawing from account {}", accountId, dae);
            throw new DatabaseException("Error occurred while processing withdrawal", dae);
        } catch (Exception e) {
            logger.error("Unexpected error during withdrawal from account {}", accountId, e);
            throw new RuntimeException("An unexpected error occurred during withdrawal", e);
        }
    }

    @Override
    @Transactional
    public void transfer(UUID fromAccountId, UUID toAccountId, BigDecimal amount) {
        if (fromAccountId.equals(toAccountId)) {
            logger.warn("Attempted to transfer between the same account: {}", fromAccountId);
            throw new InvalidUserInputException("Cannot transfer to the same account");
        }
        
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            logger.warn("Invalid transfer amount: {}", amount);
            throw new InvalidUserInputException("Transfer amount must be positive");
        }
        
        try {
            // Verify both accounts exist
            Account fromAccount = findAccountById(fromAccountId);
            Account toAccount = findAccountById(toAccountId);
            
            // Check sufficient funds
            if (fromAccount.getBalance().compareTo(amount) < 0) {
                logger.warn("Insufficient funds in account {} for transfer of {}", fromAccountId, amount);
                throw new InsufficientFundsException("Insufficient funds for transfer");
            }
            
            // Perform withdrawal
            BigDecimal fromNewBalance = fromAccount.getBalance().subtract(amount);
            int withdrawResult = dao.updateBalance(fromAccountId, fromNewBalance);
            
            if (withdrawResult <= 0) {
                logger.error("Failed to withdraw during transfer");
                throw new DatabaseException("Failed to complete transfer: withdrawal failed");
            }
            
            // Perform deposit
            BigDecimal toNewBalance = toAccount.getBalance().add(amount);
            int depositResult = dao.updateBalance(toAccountId, toNewBalance);
            
            if (depositResult <= 0) {
                logger.error("Failed to deposit during transfer");
                // This is serious - the money was withdrawn but not deposited
                // In a real system, we would need more sophisticated error handling here
                throw new DatabaseException("Critical error: Money withdrawn but not deposited.");
            }
            
            // Record the transfer transaction in the database
            transactionService.recordTransfer(fromAccountId, toAccountId, amount);
            logger.info("Transferred {} from account {} to account {}", amount, fromAccountId, toAccountId);
            
        } catch (DataAccessException dae) {
            logger.error("Database error during transfer between accounts {} and {}", fromAccountId, toAccountId, dae);
            throw new DatabaseException("Error occurred while processing transfer", dae);
        } catch (Exception e) {
            logger.error("Unexpected error during transfer between accounts {} and {}", fromAccountId, toAccountId, e);
            throw new RuntimeException("An unexpected error occurred during transfer", e);
        }
    }

    // *************** Account Management Methods *************************

    @Override
    @Transactional
    public DatabaseRequestResult deleteAccount(UUID accountId) {
        try {
            // Verify account exists
            findAccountById(accountId);
            
            int rowsAffected = dao.deleteAccount(accountId);
            if (rowsAffected > 0) {
                logger.info("Deleted account {}", accountId);
            } else {
                logger.error("Failed to delete account {}", accountId);
            }
            
            return new DatabaseRequestResult(rowsAffected);
        } catch (DataAccessException dae) {
            logger.error("Database error while deleting account {}", accountId, dae);
            throw new DatabaseException("Error occurred while deleting account", dae);
        } catch (Exception e) {
            logger.error("Unexpected error while deleting account {}", accountId, e);
            throw new RuntimeException("An unexpected error occurred while deleting account", e);
        }
    }

    // *************** Helper Methods *************************

    @Override
    public BigDecimal getBalance(UUID accountId) {
        Account account = findAccountById(accountId);
        logger.debug("Retrieved balance for account {}: {}", accountId, account.getBalance());
        return account.getBalance();
    }

}
