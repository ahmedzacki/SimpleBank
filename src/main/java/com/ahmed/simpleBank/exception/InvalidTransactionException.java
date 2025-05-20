package com.ahmed.simpleBank.exception;

/**
 * Exception thrown when a transaction violates business rules
 */
public class InvalidTransactionException extends RuntimeException {
    
    private final String transactionType;
    private final String reason;
    
    public InvalidTransactionException(String transactionType, String reason) {
        super("Invalid " + transactionType + " transaction: " + reason);
        this.transactionType = transactionType;
        this.reason = reason;
    }
    
    public String getTransactionType() {
        return transactionType;
    }
    
    public String getReason() {
        return reason;
    }
}