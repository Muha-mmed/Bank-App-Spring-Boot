package com.muhd.bank_app_api.service;

import java.util.List;

import com.muhd.bank_app_api.model.BankTransaction;

public interface BankTransactionService {

    public BankTransaction makeTransaction(String senderAccount, String receiverAccount, double amount, String description);

    public List<BankTransaction> getUserTransactions(String acc);
}