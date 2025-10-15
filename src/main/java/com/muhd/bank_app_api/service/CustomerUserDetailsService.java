package com.muhd.bank_app_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.muhd.bank_app_api.model.BankUser;
import com.muhd.bank_app_api.repository.BankUserRepo;

public class CustomerUserDetailsService implements UserDetailsService{

    @Autowired
    private final BankUserRepo userRepo;

    public CustomerUserDetailsService(BankUserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        BankUser user = userRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("email doesn't exist"));
        
        return new CustomUserDetail(user);
    }
    
}
