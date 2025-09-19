package com.muhd.bank_app_api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.muhd.bank_app_api.model.Account;

@Service
public interface AccountService {

    public Account createAccount(Account account);
    public Account getAccountDetailsByAccountNumber(String accountNumber);
    public List<Account> getAllAccountDetails();
    public Account depositAmount(String accountNumber, Double amount);
    public Account withdrawAmount(String accountNumber, Double amount);
    public String closeAccount(Long accountNumber);

}
