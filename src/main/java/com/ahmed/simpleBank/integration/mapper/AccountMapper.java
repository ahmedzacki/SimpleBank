package com.ahmed.simpleBank.integration.mapper;

import com.ahmed.simpleBank.business.Account;

import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Mapper
public interface AccountMapper {
    
    @Insert("INSERT INTO accounts (accountId, userId, accountType, balance, createdAt) " +
            "VALUES (#{accountId}, #{userId}, #{accountType}, #{balance}, #{createdAt})")
    int queryForCreateAccount(Account account);

    @Select("SELECT accountId, userId, accountType, balance, createdAt " +
            "FROM accounts WHERE accountId = #{accountId}")
    Account queryForGetAccountById(UUID accountId);

    @Select("SELECT accountId, userId, accountType, balance, createdAt " +
            "FROM accounts WHERE userId = #{userId}")
    List<Account> queryForGetAllAccountsByUserId(UUID userId);

    @Update("UPDATE accounts SET balance = #{balance} " +
            "WHERE accountId = #{accountId}")
    int queryForUpdateBalance(@Param("accountId") UUID accountId, 
                            @Param("balance") BigDecimal balance);

    @Delete("DELETE FROM accounts WHERE accountId = #{accountId}")
    int queryForDeleteAccount(UUID accountId);

    @Select("SELECT accountId, userId, accountType, balance, createdAt " +
            "FROM accounts WHERE userId = #{userId} AND accountType = #{accountType}")
    List<Account> queryForGetAccountsByUserIdAndType(@Param("userId") UUID userId,
                                                     @Param("accountType") String accountType);

    @Select("SELECT EXISTS(SELECT 1 FROM accounts WHERE userId = #{userId} " +
            "AND accountType = #{accountType})")
    boolean queryForAccountExists(@Param("userId") UUID userId, 
                                @Param("accountType") String accountType);
}