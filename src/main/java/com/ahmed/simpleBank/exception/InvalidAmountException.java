package com.ahmed.simpleBank.exception;

/**
 * Exception thrown when a transaction amount is invalid
 */
public class InvalidAmountException extends RuntimeException {

    public InvalidAmountException(String message) {
        super(message);
    }

    public InvalidAmountException(String message, Throwable cause) {
        super(message, cause);
    }
}