package com.ahmed.simpleBank.business;

import java.util.Arrays;

public enum TransactionTypeEnum {
    DEPOSIT("DEPOSIT"),       // Money coming into an account (to_account only)
    WITHDRAWAL("WITHDRAWAL"), // Money leaving an account (from_account only)
    TRANSFER("TRANSFER"),     // Money moving between accounts (both accounts)
    INTEREST("INTEREST"),     // Interest payment to an account (to_account only)
    FEE("FEE");              // Fee charged to an account (from_account only)

    private final String value;

    TransactionTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static TransactionTypeEnum fromString(String text) {
        return Arrays.stream(TransactionTypeEnum.values())
                .filter(type -> type.value.equalsIgnoreCase(text))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown transaction type: " + text));
    }

    @Override
    public String toString() {
        return value;
    }
}