package com.ahmed.simpleBank.business;

public enum AccountTypeEnum {
    CHECKING("Checking"),
    SAVINGS("Savings");

    private final String accountType;

    AccountTypeEnum(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountType() {
        return accountType;
    }

    public static AccountTypeEnum fromString(String accountType) {
        for (AccountTypeEnum type : AccountTypeEnum.values()) {
            if (type.getAccountType().equalsIgnoreCase(accountType)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid AccountType: " + accountType);
    }
}
