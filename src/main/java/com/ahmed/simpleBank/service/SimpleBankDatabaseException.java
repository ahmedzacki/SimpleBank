package com.ahmed.simpleBank.service;

public class SimpleBankDatabaseException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public SimpleBankDatabaseException() {
        super();
    }

    public SimpleBankDatabaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public SimpleBankDatabaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public SimpleBankDatabaseException(String message){
        super(message);
    }

    public SimpleBankDatabaseException(Throwable cause) {
        super(cause);
    }
}
