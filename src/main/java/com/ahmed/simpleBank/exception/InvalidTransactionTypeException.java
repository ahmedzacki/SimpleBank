package com.ahmed.simpleBank.exception;

/**
 * Exception thrown when an account type is invalid
 */
public class InvalidTransactionTypeException extends RuntimeException {

    private final String transactionType;

    public InvalidTransactionTypeException(String transactionType) {
        super("Invalid transaction type: " + transactionType);
        this.transactionType = transactionType;
    }

    public InvalidTransactionTypeException(String transactionType, String message) {
        super(message);
        this.transactionType = transactionType;
    }

    public String getTransactionType() {
        return transactionType;
    }
}