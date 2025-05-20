package com.ahmed.simpleBank.utils;

import java.math.BigDecimal;
import java.util.UUID;

import com.ahmed.simpleBank.business.AccountTypeEnum;
import com.ahmed.simpleBank.business.Transaction;
import com.ahmed.simpleBank.business.TransactionTypeEnum;
import com.ahmed.simpleBank.dto.UserDTO;
import com.ahmed.simpleBank.exception.AccountNotFoundException;
import com.ahmed.simpleBank.exception.InvalidTransactionTypeException;
import com.ahmed.simpleBank.exception.InvalidAmountException;
import com.ahmed.simpleBank.exception.InvalidTransactionException;
import com.ahmed.simpleBank.exception.InvalidUserInputException;
import com.ahmed.simpleBank.integration.AccountDao;

public class Validation {

    /********************************** Utility Methods **********************************************/

    public static void validateDTOForCreate(UserDTO user){
        if (user == null ||
                isNullOrBlank(user.getFirstName()) ||
                isNullOrBlank(user.getLastName()) ||
                isNullOrBlank(user.getEmail()) ||
                isNullOrBlank(user.getPassword()) ||
                !user.getEmail().contains("@") ||
                isNullOrBlank(user.getRole())) {
            throw new InvalidUserInputException("User is not fully populated");
        }
    }

    private static boolean isNullOrBlank(String s) {
        return s == null || s.isBlank();
    }

    public static void validateTransaction(Transaction transaction) {
        TransactionTypeEnum type = transaction.getTransactionType();
        UUID fromAccountId = transaction.getFromAccountId();
        UUID toAccountId = transaction.getToAccountId();

        switch (type) {
            case DEPOSIT:
            case INTEREST:
                if (fromAccountId != null || toAccountId == null) {
                    throw new InvalidTransactionException(
                            type.toString(),
                            "Must have null source account and valid destination account");
                }
                break;
            case WITHDRAWAL:
            case FEE:
                if (fromAccountId == null || toAccountId != null) {
                    throw new InvalidTransactionException(
                            type.toString(),
                            "Must have valid source account and null destination account");
                }
                break;
            case TRANSFER:
                if (fromAccountId == null || toAccountId == null) {
                    throw new InvalidTransactionException(
                            type.toString(),
                            "Must have both source and destination accounts");
                }
                break;
            default:
                throw new InvalidTransactionException(
                        "UNKNOWN",
                        "Unrecognized transaction type: " + type);
        }
    }
    

    public static void validateAccountId(UUID accountId, AccountDao accountDao) {
        if (accountId == null) {
            throw new InvalidUserInputException("Account ID cannot be null");
        }

        if (accountDao.getAccountById(accountId) == null) {
            throw new AccountNotFoundException("Account not found with ID: " + accountId);
        }
    }

    public static void validateAccountType(String accountType) {
        if (accountType == null || accountType.trim().isEmpty()) {
            throw new InvalidTransactionTypeException(accountType, "Account type cannot be null or empty");
        }

        try {
            AccountTypeEnum.fromString(accountType);
        } catch (IllegalArgumentException e) {
            throw new InvalidTransactionTypeException(accountType, "Unknown account type: " + accountType);
        }
    }
    
    public static void validateTransactionType(TransactionTypeEnum transactionType) {
        if (transactionType == null) {
            throw new InvalidTransactionTypeException(null, "Transaction type cannot be null");
        }
    }

    /**
 * Validates that a transaction amount is valid
 */
public static void validateAmount(BigDecimal amount) {
    if (amount == null) {
        throw new InvalidAmountException("Transaction amount cannot be null");
    }
    
    if (amount.compareTo(BigDecimal.ZERO) <= 0) {
        throw new InvalidAmountException("Transaction amount must be greater than zero, got: " + amount);
    }
    
    // Optional: Check for a reasonable maximum amount
    if (amount.compareTo(new BigDecimal("1000000")) > 0) {
        throw new InvalidAmountException("Transaction amount exceeds maximum allowed: " + amount);
    }
    
    // Optional: Check for precision/scale issues
    if (amount.scale() > 4) {
        throw new InvalidAmountException("Transaction amount has too many decimal places: " + amount);
    }
}
    
}
