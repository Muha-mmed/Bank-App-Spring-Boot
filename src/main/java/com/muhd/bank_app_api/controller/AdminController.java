package com.muhd.bank_app_api.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.muhd.bank_app_api.model.BankUser;
import com.muhd.bank_app_api.repository.BankUserRepo;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private BankUserRepo bankUserRepo;

    @PreAuthorize("#email == authentication.principal.username")
    @GetMapping("/{email}")
    public ResponseEntity<BankUser> getAccountDetailsByAccountNumber(@PathVariable String email) {
        BankUser getAccountDetail = bankUserRepo.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("user not found"));
        return ResponseEntity.ok(getAccountDetail);
    }
}
