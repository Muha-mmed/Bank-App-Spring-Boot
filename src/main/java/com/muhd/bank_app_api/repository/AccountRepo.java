package com.muhd.bank_app_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.muhd.bank_app_api.model.Account;

@Repository
public interface AccountRepo extends JpaRepository<Account,Long>{
    
}
