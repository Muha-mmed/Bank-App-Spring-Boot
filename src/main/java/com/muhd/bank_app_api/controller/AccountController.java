package com.muhd.bank_app_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.muhd.bank_app_api.dto.AccountDetailResponseDTO;
import com.muhd.bank_app_api.model.Account;
import com.muhd.bank_app_api.model.BankUser;
import com.muhd.bank_app_api.repository.BankUserRepo;
import com.muhd.bank_app_api.service.AccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/user/account")
public class AccountController {
    
    @Autowired
    private AccountService service;

    @Autowired
    private BankUserRepo bankUserRepo;

    @GetMapping("/me")
    public ResponseEntity<AccountDetailResponseDTO> getCurrentUser(Authentication authentication) {
    String email = authentication.getName(); // Comes from JWT subject
    BankUser user = bankUserRepo.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    AccountDetailResponseDTO response = new AccountDetailResponseDTO();
    response.setEmail(user.getEmail());
    response.setFirstName(user.getFirstName());
    response.setLastName(user.getLastName());
    response.setAccountBalance(user.getAccount().getAccountBalance());
    response.setAccountNumber(user.getAccount().getAccountNumber());

    return ResponseEntity.ok(response);
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
