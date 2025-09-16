package com.muhd.bank_app_api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.muhd.bank_app_api.model.Account;

@Service
public interface AccountService {

    public Account createAccount(Account account);
    public Account getAccountDetailsByAccountNumber(Long accountNumber);
    public List<Account> getAllAccountDetails();
    public Account depositAmount(Long accountNumber, Double amount);
    public Account withdrawAmount(Long accountNumber, Double amount);
    public String closeAccount(Long accountNumber);

}
