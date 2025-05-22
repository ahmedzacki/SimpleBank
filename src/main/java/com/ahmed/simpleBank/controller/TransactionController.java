package com.ahmed.simpleBank.controller;

import com.ahmed.simpleBank.business.Transaction;
import com.ahmed.simpleBank.business.TransactionTypeEnum;
import com.ahmed.simpleBank.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    
    private final TransactionService service;
    
    @Autowired
    public TransactionController(TransactionService service) {
        this.service = service;
    }
    
    @GetMapping(value = "/{transactionId}", 
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Transaction> getTransactionById(@PathVariable UUID transactionId) {
        Transaction transaction = service.getTransactionById(transactionId);
        return ResponseEntity.ok().body(transaction);
    }
    
    @GetMapping(value = "/account/{accountId}", 
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<Transaction>> getTransactionsByAccountId(@PathVariable UUID accountId) {
        List<Transaction> transactions = service.getTransactionsByAccountId(accountId);
        return ResponseEntity.ok().body(transactions);
    }
    
    @GetMapping(value = "/type/{type}", 
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<Transaction>> getTransactionsByType(@PathVariable String type) {
        TransactionTypeEnum transactionType = TransactionTypeEnum.fromString(type);
        List<Transaction> transactions = service.getTransactionsByType(transactionType);
        return ResponseEntity.ok().body(transactions);
    }
    
    @GetMapping(value = "/account/{accountId}/type/{type}", 
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<Transaction>> getTransactionsByAccountIdAndType(
            @PathVariable UUID accountId, 
            @PathVariable String type) {
        TransactionTypeEnum transactionType = TransactionTypeEnum.fromString(type);
        List<Transaction> transactions = service.getTransactionsByAccountIdAndType(accountId, transactionType);
        return ResponseEntity.ok().body(transactions);
    }
}
