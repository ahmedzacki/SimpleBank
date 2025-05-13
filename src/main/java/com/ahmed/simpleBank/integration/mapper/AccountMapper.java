package com.ahmed.simpleBank.integration.mapper;

import com.ahmed.simpleBank.business.Account;
import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Mapper
public interface AccountMapper {
    
    @Insert("INSERT INTO accounts (userId, accountType, balance) " +
            "VALUES (#{userId}, #{accountType.accountType}, #{balance})")
    @Options(useGeneratedKeys = true, keyProperty = "accountId")
    int queryForCreateAccount(Account account);

    @Select("SELECT accountId, userId, accountType, balance, createdAt FROM accounts WHERE accountId = #{accountId}")
    Account queryForGetAccountById(int accountId);

    @Select("SELECT accountId, userId, accountType, balance, createdAt FROM accounts WHERE userId = #{userId}")
    List<Account> queryForGetAccountsByUserId(UUID userId);

    @Update("UPDATE accounts SET balance = #{balance} " +
            "WHERE accountId = #{accountId}")
    int queryForUpdateBalance(@Param("accountId") int accountId, 
                     @Param("balance") BigDecimal balance);

    @Delete("DELETE FROM accounts WHERE accountId = #{accountId}")
    int queryForDeleteAccount(int accountId);

    @Select("SELECT accountId, userId, accountType, balance, createdAt FROM accounts WHERE userId = #{userId} AND accountType = #{accountType}")
    Account queryForGetAccountByUserIdAndType(@Param("userId") UUID userId, 
                                    @Param("accountType") String accountType);

    @Select("SELECT EXISTS(SELECT 1 FROM accounts WHERE userId = #{userId} " +
            "AND accountType = #{accountType})")
    boolean queryForAccountExists(@Param("userId") UUID userId, 
                        @Param("accountType") String accountType);
}
