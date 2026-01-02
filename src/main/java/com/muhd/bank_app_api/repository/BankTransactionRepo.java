package com.muhd.bank_app_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.muhd.bank_app_api.model.Account;
import com.muhd.bank_app_api.model.BankTransaction;


public interface BankTransactionRepo extends JpaRepository<BankTransaction,Long>{

List<BankTransaction> findBySenderAccountOrReceiverAccount(Account senderAccount, Account receiverAccount);
}