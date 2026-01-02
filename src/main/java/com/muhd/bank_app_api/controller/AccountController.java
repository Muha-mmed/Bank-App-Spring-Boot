package com.muhd.bank_app_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.muhd.bank_app_api.dto.AccountDetailResponseDTO;
import com.muhd.bank_app_api.model.Account;
import com.muhd.bank_app_api.model.BankTransaction;
import com.muhd.bank_app_api.model.BankUser;
import com.muhd.bank_app_api.repository.BankUserRepo;
import com.muhd.bank_app_api.service.AccountService;
import com.muhd.bank_app_api.service.serviceImplementation.BankTransactionServiceImpl;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/user/account")
public class AccountController {
    @Autowired
    private AccountService service;

    @Autowired
    private BankUserRepo bankUserRepo;

    @Autowired
    private BankTransactionServiceImpl tServiceImpl;

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
    public List<Account> getAccountDetails() {
        List<Account> getAccountDetail = service.getAllAccountDetails();
        return getAccountDetail;
    }

    @PutMapping("/transfer/{receiverAcc}/{amount}")
    public ResponseEntity<String> transferMoney(Authentication auth, @PathVariable String receiverAcc, @PathVariable Double amount) { 
        String email = auth.getName(); BankUser sender = bankUserRepo.findByEmail(email).orElseThrow(); 
        String senderAcc = sender.getAccount().getAccountNumber();
        service.transferFund(senderAcc, receiverAcc, amount);
        return ResponseEntity.ok("now go check transactions db");
    }

    @GetMapping("/transaction/me")
    public List<BankTransaction> getUserTransaction(Authentication auth) {
        String email = auth.getName();
        return tServiceImpl.getUserTransactions(email);
    }
}