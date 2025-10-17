package com.muhd.bank_app_api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.muhd.bank_app_api.model.Account;
import com.muhd.bank_app_api.service.AccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/account")
public class AccountController {
    
    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    @GetMapping("/{accountNumber}")
    public Account getAccountDetailsByAccountNumber(@PathVariable String accountNumber) {
        Account getAccountDetail = service.getAccountDetailsByAccountNumber(accountNumber);
        return getAccountDetail;
    }

    @GetMapping
    public List<Account> getAllAccountDetails() {
        List<Account> getAccountDetail = service.getAllAccountDetails();
        return getAccountDetail;
    }

    @PutMapping("/deposit/{accountNumber}/{amount}")
    public Account depositAmount(@PathVariable String accountNumber,@PathVariable Double amount){
        Account account = service.depositAmount(accountNumber,amount);
        return account;
    }

    @PutMapping("/withdraw/{accountNumber}/{amount}")
    public Account withdrawAmount(@PathVariable String accountNumber,@PathVariable Double amount){
        Account account = service.withdrawAmount(accountNumber,amount);
        return account;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> closeAccount(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(service.closeAccount(id));
    }
}
