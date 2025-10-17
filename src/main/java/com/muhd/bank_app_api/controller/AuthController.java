package com.muhd.bank_app_api.controller;

import com.muhd.bank_app_api.model.BankUser;
import com.muhd.bank_app_api.security.JwtUtil;
import com.muhd.bank_app_api.service.serviceImplementation.AuthServiceImple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthServiceImple authService;

    // 🧩 Register endpoint
    @PostMapping("/register")
    public BankUser register(@RequestBody BankUser user) {
        return authService.createUser(user);
    }

    // 🔑 Login endpoint
    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> user) {
        return authService.login(user);
    }
}
