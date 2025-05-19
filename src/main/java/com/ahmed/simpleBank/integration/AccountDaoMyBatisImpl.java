package com.ahmed.simpleBank.integration;

import com.ahmed.simpleBank.business.Account;
import com.ahmed.simpleBank.integration.mapper.AccountMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Repository("AccountDao")
public class AccountDaoMyBatisImpl implements AccountDao {

    private final AccountMapper mapper;

    public AccountDaoMyBatisImpl(AccountMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public int createAccount(Account account) {
        return mapper.queryForCreateAccount(account);
    }

    @Override
    public Account getAccountById(UUID accountId) {
        return mapper.queryForGetAccountById(accountId);
    }

    @Override
    public List<Account> getAllAccountsByUserId(UUID userId) {
        return mapper.queryForGetAllAccountsByUserId(userId);
    }

    @Override
    public int updateBalance(UUID accountId, BigDecimal balance) {
        return mapper.queryForUpdateBalance(accountId, balance);
    }

    @Override
    public int deleteAccount(UUID accountId) {
        return mapper.queryForDeleteAccount(accountId);
    }

    @Override
    public List<Account> getAccountsByUserIdAndType(UUID userId, String accountType) {
        return mapper.queryForGetAccountsByUserIdAndType(userId, accountType);
    }

    @Override
    public Boolean accountExists(UUID userId, String accountType) {
        return mapper.queryForAccountExists(userId, accountType);
    }
}
