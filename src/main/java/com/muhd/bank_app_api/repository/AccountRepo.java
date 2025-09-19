package com.muhd.bank_app_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.muhd.bank_app_api.model.Account;


@Repository
public interface AccountRepo extends JpaRepository<Account,Long>{
    Optional<Account> findByAccountNumber(String accountNumber);
}
