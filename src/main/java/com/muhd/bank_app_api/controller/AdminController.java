package com.muhd.bank_app_api.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.muhd.bank_app_api.model.Account;
import com.muhd.bank_app_api.model.BankUser;
import com.muhd.bank_app_api.repository.BankUserRepo;
import com.muhd.bank_app_api.service.AccountService;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private BankUserRepo bankUserRepo;

    @Autowired
    private AccountService service;

    @PreAuthorize("#email == authentication.principal.username")
    @GetMapping("/{email}")
    public ResponseEntity<BankUser> getAccountDetailsByAccountNumber(@PathVariable String email) {
        BankUser getAccountDetail = bankUserRepo.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("user not found"));
        return ResponseEntity.ok(getAccountDetail);
    }

    @GetMapping
    public List<Account> getAccountDetails() {
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
