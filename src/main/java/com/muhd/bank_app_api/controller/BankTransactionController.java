package com.muhd.bank_app_api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.muhd.bank_app_api.model.BankTransaction;
import com.muhd.bank_app_api.service.serviceImplementation.BankTransactionServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class BankTransactionController{

    private final BankTransactionServiceImpl tServiceImpl;

    @PostMapping("/transfer")
    public ResponseEntity<BankTransaction> makeTransfer(@RequestParam String senderEmail,
                                                    @RequestParam String receiverEmail,
                                                    @RequestParam double amount,
                                                    @RequestParam String title) {
        BankTransaction tx = tServiceImpl.makeTransaction(senderEmail, receiverEmail, amount, title);
        return ResponseEntity.ok(tx);
    }

    @GetMapping("/{email}")
    public List<BankTransaction> getUserTransactions(@PathVariable String email) {
        return tServiceImpl.getUserTransactions(email);
    }
}
