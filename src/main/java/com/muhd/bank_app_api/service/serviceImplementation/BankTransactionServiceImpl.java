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
    public BankTransaction makeTransaction(String senderEmail, String receiverEmail, double amount, String title) {
        // ✅ 1. Fetch sender & receiver
        BankUser sender = bankUserRepo.findByEmail(senderEmail)
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        BankUser receiver = bankUserRepo.findByEmail(receiverEmail)
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        Account senderAccount = sender.getAccount();
        Account receiverAccount = receiver.getAccount();

        if (senderAccount == null || receiverAccount == null) {
            throw new RuntimeException("Both users must have valid accounts");
        }

        // ✅ 2. Check sender balance
        if (senderAccount.getAccountBalance() < amount) {
            throw new RuntimeException("Insufficient balance");
        }

        // ✅ 3. Deduct & add funds
        senderAccount.setAccountBalance(senderAccount.getAccountBalance() - amount);
        receiverAccount.setAccountBalance(receiverAccount.getAccountBalance() + amount);

        // ✅ 4. Save updated accounts
        accountRepo.save(senderAccount);
        accountRepo.save(receiverAccount);

        // ✅ 5. Record the transaction
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
