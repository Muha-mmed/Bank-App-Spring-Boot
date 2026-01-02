package com.muhd.bank_app_api.service.serviceImplementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.muhd.bank_app_api.model.Account;
import com.muhd.bank_app_api.repository.AccountRepo;
import com.muhd.bank_app_api.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountRepo repo;

    @Autowired
    BankTransactionServiceImpl tService;

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
    public Account transferFund(String senderAccount, String receiverAccount, double amount) {
        Optional<Account> sAccount = repo.findByAccountNumber(senderAccount);
        Optional<Account> rAccount = repo.findByAccountNumber(receiverAccount);
        if (sAccount.isEmpty()) {
            throw new RuntimeException("Account is not present");
        }
        Account getSenderAccount = sAccount.get();
        Account getReceiverAccount = rAccount.get();
        if (amount > 0) {
            double totalReceiverBalance = getReceiverAccount.getAccountBalance() + amount;
            getReceiverAccount.setAccountBalance(totalReceiverBalance);
            double totalSenderBalance = getSenderAccount.getAccountBalance() - amount;
            getSenderAccount.setAccountBalance(totalSenderBalance);
            repo.save(getReceiverAccount);
            repo.save(getSenderAccount);
        }
        tService.makeTransaction(senderAccount, receiverAccount, amount, "transfer");
        return getSenderAccount;
    }

    @Override
    public Account depositAmount(String accountNumber, Double amount) {
        Optional<Account> account = repo.findByAccountNumber(accountNumber);
        if (account.isEmpty()) {
            throw new RuntimeException("Account is not present");
        }
        Account getAccount = account.get();
        if (amount > 0) {
            double totalBalance = getAccount.getAccountBalance() + amount;
            getAccount.setAccountBalance(totalBalance);
            repo.save(getAccount);
        }
        return getAccount;
    }

    @Override
    public Account withdrawAmount(String accountNumber, Double amount) {
        Optional<Account> account = repo.findByAccountNumber(accountNumber);
        if (account.isEmpty()) {
            throw new RuntimeException("Account is not present");
        }
        Account getAccount = account.get();
        if (getAccount.getAccountBalance() > 0) {
            double totalBalance = getAccount.getAccountBalance() - amount;
            getAccount.setAccountBalance(totalBalance);
            repo.save(getAccount);
        } else {
            throw new RuntimeException("insufficient fund");
        }
        return getAccount;
    }

    @Override
    public String closeAccount(Long id) {
        Optional<Account> account = repo.findById(id);
        if (account.isEmpty()) {
            throw new RuntimeException("Account is not present");
        }
        repo.deleteById(id);
        return "deteled";
    }

    @Override
    public Account transferMoney(String senderAccNumber, String receiverAccNumber, Double amount) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'transferMoney'");
    }

}