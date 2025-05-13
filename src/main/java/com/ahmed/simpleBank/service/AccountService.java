package com.ahmed.simpleBank.service;

import com.ahmed.simpleBank.business.Account;
import com.ahmed.simpleBank.business.AccountTypeEnum;
import com.ahmed.simpleBank.business.CheckingAccount;
import com.ahmed.simpleBank.business.SavingsAccount;
import com.ahmed.simpleBank.business.User;
import com.ahmed.simpleBank.integration.AccountDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class AccountService {

    private final AccountDao dao;

    public AccountService(AccountDao dao) {
        this.dao = dao;
    }

    // Create a new account with initial balance
    @Transactional
    public int createAccount(User user, AccountTypeEnum type, BigDecimal balance) {
        // Check if account already exists
        Boolean existsResult = dao.accountExists(user.getUserId(), type.getAccountType());
        if (existsResult) {
            throw new IllegalArgumentException("Account of this type already exists for user");
        }
        Account newAccount;
        if (AccountTypeEnum.CHECKING.equals(type)) {
            newAccount = new CheckingAccount(1234, user.getUserId(), new java.sql.Timestamp(System.currentTimeMillis()));
        } else if (AccountTypeEnum.SAVINGS.equals(type)) {
            newAccount = new SavingsAccount(56789, user.getUserId(), new java.sql.Timestamp(System.currentTimeMillis()));
        } else {
            throw new IllegalArgumentException("Invalid account type");
        }

        return dao.createAccount(newAccount);
    }

}
