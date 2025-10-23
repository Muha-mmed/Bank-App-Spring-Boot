package com.muhd.bank_app_api.service;

import java.util.List;

import com.muhd.bank_app_api.model.BankTransaction;

public interface BankTransactionService {

    public BankTransaction makeTransaction(String senderEmail, String receiverEmail, double amount, String title);

    public List<BankTransaction> getUserTransactions(String email);
}
