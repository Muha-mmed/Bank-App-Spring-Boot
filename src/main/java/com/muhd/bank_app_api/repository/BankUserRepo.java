package com.muhd.bank_app_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.muhd.bank_app_api.model.BankUser;

public interface BankUserRepo extends JpaRepository<BankUser,Long>{

}
