package com.ahmed.simpleBank.controller;

import com.ahmed.simpleBank.business.Account;
import com.ahmed.simpleBank.business.AccountTypeEnum;
import com.ahmed.simpleBank.dto.AccountOperationDTO;
import com.ahmed.simpleBank.dto.TransferDTO;
import com.ahmed.simpleBank.dto.BalanceResponseDTO;
import com.ahmed.simpleBank.service.AccountService;
import com.ahmed.simpleBank.service.CommonService;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService service;
    private final CommonService commonService;

    public AccountController(AccountService service, CommonService commonService) {
        this.service = service;
        this.commonService = commonService;
    }

    // *************** Account Creation Endpoints *************************
    
    @PostMapping(value = "",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<DatabaseRequestResult> createAccount(
            @RequestParam UUID userId,
            @RequestParam String accountType) {

        AccountTypeEnum type = AccountTypeEnum.fromString(accountType);
        DatabaseRequestResult result = commonService.createAccount(userId, type);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    // *************** Account Retrieval Endpoints *************************
    
    @GetMapping(value = "/{accountId}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Account> getAccountById(@PathVariable UUID accountId) {
        Account account = service.findAccountById(accountId);
        return ResponseEntity.ok().body(account);
    }
    
    @GetMapping(value = "/user/{userId}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<Account>> getAllAccountsByUserId(@PathVariable UUID userId) {
        List<Account> accounts = service.findAllAccountsByUserId(userId);
        if (accounts == null || accounts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok().body(accounts);
    }
    
    @GetMapping(value = "/user/{userId}/type/{accountType}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<Account>> getAccountsByUserIdAndType(
            @PathVariable UUID userId,
            @PathVariable String accountType) {
        
        AccountTypeEnum type = AccountTypeEnum.fromString(accountType);
        List<Account> accounts = service.findAccountsByUserIdAndType(userId, type);
        return ResponseEntity.ok().body(accounts);  
    }
    
    @GetMapping(value = "/{accountId}/balance",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<BalanceResponseDTO> getBalance(@PathVariable UUID accountId) {
        BigDecimal balance = service.getBalance(accountId);
        BalanceResponseDTO response = new BalanceResponseDTO(balance);
        return ResponseEntity.ok().body(response);
    }

    // *************** Account Operation Endpoints *************************
    
    @PostMapping(value = "/{accountId}/deposit",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<DatabaseRequestResult> deposit(
            @PathVariable UUID accountId,
            @RequestBody AccountOperationDTO operationDTO) {
        
        DatabaseRequestResult result = service.deposit(accountId, operationDTO.getAmount());
        return ResponseEntity.ok().body(result);
    }
    
    @PostMapping(value = "/{accountId}/withdraw",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<DatabaseRequestResult> withdraw(
            @PathVariable UUID accountId,
            @RequestBody AccountOperationDTO operationDTO) {
        
        DatabaseRequestResult result = service.withdraw(accountId, operationDTO.getAmount());
        return ResponseEntity.ok().body(result);
    }
    
    @PostMapping(value = "/transfer",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Void> transfer(@RequestBody TransferDTO transferDTO) {
        service.transfer(
                transferDTO.getFromAccountId(),
                transferDTO.getToAccountId(),
                transferDTO.getAmount()
        );
        return ResponseEntity.ok().build();
    }

    // *************** Account Management Endpoints *************************
    
    @DeleteMapping(value = "/{accountId}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<DatabaseRequestResult> deleteAccount(@PathVariable UUID accountId) {
        DatabaseRequestResult result = service.deleteAccount(accountId);
        return ResponseEntity.ok().body(result);
    }
}
