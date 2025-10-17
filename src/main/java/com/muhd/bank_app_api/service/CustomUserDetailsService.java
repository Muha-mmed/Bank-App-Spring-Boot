package com.muhd.bank_app_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.muhd.bank_app_api.model.BankUser;
import com.muhd.bank_app_api.repository.BankUserRepo;

@Service
public class CustomUserDetailsService implements UserDetailsService{

    @Autowired
    private BankUserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        BankUser user = userRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("email doesn't exist"));
        return new CustomUserDetail(user);
    }
}
