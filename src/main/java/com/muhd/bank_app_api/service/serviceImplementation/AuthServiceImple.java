package com.muhd.bank_app_api.service.serviceImplementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.muhd.bank_app_api.dto.LoginDTO;
import com.muhd.bank_app_api.dto.LoginResponseDTO;
import com.muhd.bank_app_api.dto.RegisterDTO;
import com.muhd.bank_app_api.dto.RegisterResponseDTO;
import com.muhd.bank_app_api.model.Account;
import com.muhd.bank_app_api.model.BankUser;
import com.muhd.bank_app_api.repository.AccountRepo;
import com.muhd.bank_app_api.repository.BankUserRepo;
import com.muhd.bank_app_api.security.JwtUtil;
import com.muhd.bank_app_api.service.AuthService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AuthServiceImple implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private BankUserRepo bRepo;

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private PasswordEncoder encoder;


    @Override
    public RegisterResponseDTO createUser(RegisterDTO dto) {
        // Convert DTO → Entity
        BankUser user = new BankUser();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setPassword(encoder.encode(dto.getPassword()));
        user.setDisable(false);
        user.setFreeze(false);
        user.setRole("USER");

        // Handle account creation for USER role
        if (user.getRole().equalsIgnoreCase("USER")) {
            Account account = new Account();
            Account savedAccount = accountRepo.save(account);

            String accNum = "202520" + String.format("%03d", savedAccount.getId());
            savedAccount.setAccountNumber(accNum);
            accountRepo.save(savedAccount);

            user.setAccount(savedAccount);
            savedAccount.setBankUser(user);
        }

        BankUser savedUser = bRepo.save(user);

        // Convert back Entity → DTO
        RegisterResponseDTO response = new RegisterResponseDTO();
        response.setFirstName(savedUser.getFirstName());
        response.setLastName(savedUser.getLastName());
        response.setEmail(savedUser.getEmail());
        response.setAccount(savedUser.getAccount());

        return response;
    }

    @Override
    public LoginResponseDTO login(LoginDTO userData) {
        String email = userData.getEmail();
        String password = userData.getPassword();

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        // ✅ Get the user from database
        BankUser user = bRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // ✅ Generate token using the whole user object
        String token = jwtUtil.generateToken(user);

        return new LoginResponseDTO(token);
    }

}