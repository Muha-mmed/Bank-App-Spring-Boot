package com.muhd.bank_app_api.service.serviceImplementation;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.muhd.bank_app_api.model.Account;
import com.muhd.bank_app_api.model.BankUser;
import com.muhd.bank_app_api.repository.AccountRepo;
import com.muhd.bank_app_api.repository.BankUserRepo;
import com.muhd.bank_app_api.security.JwtUtil;
import com.muhd.bank_app_api.service.AuthService;

@Service
public class AuthServiceImple implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

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

    @Override
public Map<String, String> login(Map<String, String> userData) {
    String email = userData.get("email");
    String password = userData.get("password");

    authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(email, password)
    );

    // ✅ Get the user from database
    BankUser user = bRepo.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    // ✅ Generate token using the whole user object
    String token = jwtUtil.generateToken(user);

    return Map.of("token", token);
}

}