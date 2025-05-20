package com.ahmed.simpleBank.integration.mapper;

import com.ahmed.simpleBank.business.Transaction;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.UUID;

@Mapper
public interface TransactionMapper {
    @Insert("INSERT INTO transactions (transaction_id, from_account_id, to_account_id, amount, transaction_type, transaction_date) " +
            "VALUES (#{transactionId}, #{fromAccountId}, #{toAccountId}, #{amount}, #{transactionType}, #{transactionDate})")
    int queryForInsertTransaction(Transaction transaction);
    
    @Select("SELECT transaction_id, from_account_id, to_account_id, amount, transaction_type, transaction_date " +
            "FROM transactions WHERE transaction_id = #{transactionId}")
    @Results(id = "transactionResultMap", value = {
            @Result(property = "transactionId", column = "transaction_id"),
            @Result(property = "fromAccountId", column = "from_account_id"),
            @Result(property = "toAccountId", column = "to_account_id"),
            @Result(property = "amount", column = "amount"),
            @Result(property = "transactionType", column = "transaction_type"),
            @Result(property = "transactionDate", column = "transaction_date")
    })
    Transaction queryForGetTransactionById(UUID transactionId);
    
    @Select("SELECT transaction_id, from_account_id, to_account_id, amount, transaction_type, transaction_date " +
            "FROM transactions WHERE from_account_id = #{accountId} OR to_account_id = #{accountId} " +
            "ORDER BY transaction_date DESC")
    @ResultMap("transactionResultMap")
    List<Transaction> queryForGetTransactionsByAccountId(UUID accountId);
    
    @Select("SELECT transaction_id, from_account_id, to_account_id, amount, transaction_type, transaction_date " +
            "FROM transactions WHERE transaction_type = #{type} " +
            "ORDER BY transaction_date DESC")
    @ResultMap("transactionResultMap")
    List<Transaction> queryForGetTransactionsByType(String type);
    
    @Select("SELECT transaction_id, from_account_id, to_account_id, amount, transaction_type, transaction_date " +
            "FROM transactions WHERE (from_account_id = #{accountId} OR to_account_id = #{accountId}) " +
            "AND transaction_type = #{type} " +
            "ORDER BY transaction_date DESC")
    @ResultMap("transactionResultMap")
    List<Transaction> queryForGetTransactionsByAccountIdAndType(UUID accountId, String type);
} 