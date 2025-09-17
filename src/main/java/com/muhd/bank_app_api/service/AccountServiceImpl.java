package com.muhd.bank_app_api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.muhd.bank_app_api.model.Account;
import com.muhd.bank_app_api.repository.AccountRepo;

@Service
public class AccountServiceImpl implements AccountService{

    @Autowired
    AccountRepo repo;

    @Override
    public Account createAccount(Account account) {
      Account acc_saved = repo.save(account);
      String acc_num = "202520"+String.format("%03d", acc_saved.getId());
      acc_saved.setAccountNumber(acc_num);
      return repo.save(acc_saved);
    }

    @Override
    public Account getAccountDetailsByAccountNumber(Long accountNumber) {
        Account getDetail = repo.findById(accountNumber).orElseThrow();
        return getDetail;
    }

    @Override
    public List<Account> getAllAccountDetails() {
        List<Account> getAccounts = repo.findAll();
        return getAccounts;
    }

    @Override
    public Account depositAmount(Long accountNumber, Double amount) {
        Optional<Account> account = repo.findById(accountNumber);
        if (account.isEmpty()){
            throw new RuntimeException("Account is not present");
        }
        Account getAccount = account.get();
        if(amount > 0){
            double totalBalance = getAccount.getAccountBalance()+amount;
            getAccount.setAccountBalance(totalBalance);
            repo.save(getAccount);
        }
        return getAccount;
    }

    @Override
    public Account withdrawAmount(Long accountNumber, Double amount) {
        Optional<Account> account = repo.findById(accountNumber);
        if (account.isEmpty()){
            throw new RuntimeException("Account is not present");
        }
        Account getAccount = account.get();
        if (getAccount.getAccountBalance() > 0) {
            double totalBalance = getAccount.getAccountBalance() - amount;
            getAccount.setAccountBalance(totalBalance);
            repo.save(getAccount);
        }else {
            throw new RuntimeException("insufficient fund");
        }
        return getAccount;
    }

    @Override
    public String closeAccount(Long accountNumber) {
       Optional<Account> account = repo.findById(accountNumber);
        if (account.isEmpty()){
            throw new RuntimeException("Account is not present");
        }
        repo.deleteById(accountNumber);
        return "deteled";
    }
    
}
