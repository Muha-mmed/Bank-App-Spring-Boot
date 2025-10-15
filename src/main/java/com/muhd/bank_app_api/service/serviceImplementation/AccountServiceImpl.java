package com.muhd.bank_app_api.service.serviceImplementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.muhd.bank_app_api.model.Account;
import com.muhd.bank_app_api.repository.AccountRepo;
import com.muhd.bank_app_api.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService{

    @Autowired
    AccountRepo repo;


    @Override
    public Account getAccountDetailsByAccountNumber(String accountNumber) {
        Account getDetail = repo.findByAccountNumber(accountNumber).orElseThrow();
        return getDetail;
    }

    @Override
    public List<Account> getAllAccountDetails() {
        List<Account> getAccounts = repo.findAll();
        return getAccounts;
    }

    @Override
    public Account depositAmount(String accountNumber, Double amount) {
        Optional<Account> account = repo.findByAccountNumber(accountNumber);
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
    public Account withdrawAmount(String accountNumber, Double amount) {
        Optional<Account> account = repo.findByAccountNumber(accountNumber);
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
    public String closeAccount(Long id) {
       Optional<Account> account = repo.findById(id);
        if (account.isEmpty()){
            throw new RuntimeException("Account is not present");
        }
        repo.deleteById(id);
        return "deteled";
    }
    
}
