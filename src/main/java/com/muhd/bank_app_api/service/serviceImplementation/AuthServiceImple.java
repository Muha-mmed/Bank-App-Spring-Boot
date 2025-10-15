package com.muhd.bank_app_api.service.serviceImplementation;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.muhd.bank_app_api.model.Account;
import com.muhd.bank_app_api.model.BankUser;
import com.muhd.bank_app_api.repository.AccountRepo;
import com.muhd.bank_app_api.repository.BankUserRepo;
import com.muhd.bank_app_api.service.AuthService;

@Service
public class AuthServiceImple implements AuthService {

    private final BankUserRepo bRepo;
    private final AccountRepo accountRepo;
    private final PasswordEncoder encoder;

    public AuthServiceImple(BankUserRepo bRepo, AccountRepo accountRepo, PasswordEncoder encoder) {
        this.bRepo = bRepo;
        this.accountRepo = accountRepo;
        this.encoder = encoder;
    }

    @Override
    public BankUser createUser(BankUser bUser) {
        // Encode password
        if (bUser.getPassword() != null) {
            bUser.setPassword(encoder.encode(bUser.getPassword()));
        }

        // Default role if not set
        if (bUser.getRole() == null || bUser.getRole().isEmpty()) {
            bUser.setRole("USER");
        }

        // Handle role-based account creation
        if (bUser.getRole().equalsIgnoreCase("USER")) {
            // Create and save new account
            Account account = new Account();

            Account savedAccount = accountRepo.save(account);
            String accNum = "202520" + String.format("%03d", savedAccount.getId());
            savedAccount.setAccountNumber(accNum);
            accountRepo.save(savedAccount);

            // Link account <-> user
            bUser.setAccount(savedAccount);
            savedAccount.setBankUser(bUser);
        } else {
            // Admins or other roles don't have accounts
            bUser.setAccount(null);
        }

        // Finally, save the user
        return bRepo.save(bUser);
    }
}