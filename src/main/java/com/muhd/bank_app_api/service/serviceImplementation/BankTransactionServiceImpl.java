package com.muhd.bank_app_api.service.serviceImplementation;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.muhd.bank_app_api.model.Account;
import com.muhd.bank_app_api.model.BankTransaction;
import com.muhd.bank_app_api.model.BankUser;
import com.muhd.bank_app_api.repository.AccountRepo;
import com.muhd.bank_app_api.repository.BankTransactionRepo;
import com.muhd.bank_app_api.repository.BankUserRepo;
import com.muhd.bank_app_api.service.BankTransactionService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BankTransactionServiceImpl implements BankTransactionService{

    private final BankTransactionRepo transactionRepo;
    private final AccountRepo accountRepo;
    private final BankUserRepo bankUserRepo;

    /**
     * Transfer money between two users
     */
    @Override
    @Transactional
    public BankTransaction makeTransaction(String senderAccountNumber, String receiverAccountNumber, double amount, String title) {
    Account senderAccount = accountRepo.findByAccountNumber(senderAccountNumber)
            .orElseThrow(() -> new RuntimeException("Sender account not found"));

    Account receiverAccount = accountRepo.findByAccountNumber(receiverAccountNumber)
            .orElseThrow(() -> new RuntimeException("Receiver account not found"));

    // ✅ Create transaction
    BankTransaction tx = new BankTransaction();
    tx.setTitle(title);
    tx.setAmount(amount);
    tx.setSenderAccount(senderAccount);
    tx.setReceiverAccount(receiverAccount);
    tx.setStatus("SUCCESS");
    tx.setCreatedAt(LocalDateTime.now());

    return transactionRepo.save(tx);
}


    /**
     * Get all transactions for a user
     */
    @Override
    public List<BankTransaction> getUserTransactions(String email) {
        BankUser user = bankUserRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Account userAccount = user.getAccount();
        return transactionRepo.findBySenderAccountOrReceiverAccount(userAccount, userAccount);
    }
}
