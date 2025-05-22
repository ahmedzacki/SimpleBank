package com.ahmed.simpleBank.service;

import org.springframework.stereotype.Service;

import java.util.UUID;
import org.slf4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import com.ahmed.simpleBank.business.Account;
import com.ahmed.simpleBank.business.AccountTypeEnum;
import com.ahmed.simpleBank.business.User;
import com.ahmed.simpleBank.controller.DatabaseRequestResult;
import com.ahmed.simpleBank.exception.DatabaseException;
import com.ahmed.simpleBank.exception.UserNotFoundException;
import com.ahmed.simpleBank.integration.AccountDao;
import com.ahmed.simpleBank.integration.UserDao;

import org.springframework.transaction.annotation.Transactional;

// *************** Common Service *************************
// this class solves circular dependency between UserService and AccountService
// it is used to create a default account for a user

@Service
public class CommonService {

    @Autowired
    private Logger logger;

    private final AccountDao accountDao;
    private final UserDao userDao;
    
    @Autowired
    public CommonService(AccountDao accountDao, UserDao userDao) {
        this.accountDao = accountDao;
        this.userDao = userDao;
    }

    @Transactional
    public DatabaseRequestResult createAccount(UUID userId, AccountTypeEnum accountType) {
        try {
            // Verify user exists
            findUserById(userId);

            // Generate a UUID that doesn't already exist in the database
            UUID accountId;
            boolean idExists;
            int maxAttempts = 5; // Limit retries to prevent infinite loop
            int attempts = 0;

            do {
                accountId = UUID.randomUUID();
                // Check if this ID already exists
                Account existingAccount = accountDao.getAccountById(accountId);
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
            int rowsAffected = accountDao.createAccount(account);
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
    
    public User findUserById(UUID id) {
        try {
            User user = userDao.getUserById(id);
            if (user == null) {
                throw new UserNotFoundException(id);
            }
            logger.debug("Retrieved user: {} from the database", user);
            return user;
        } catch (DataAccessException dae) {
            logger.error("Database access error while retrieving user", dae);
            throw new DatabaseException("Error occurred while accessing database, please try again.", dae);
        }
    }
}

// *************** End of Common Service *************************