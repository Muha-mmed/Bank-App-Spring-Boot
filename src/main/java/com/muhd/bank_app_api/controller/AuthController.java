package com.muhd.bank_app_api.controller;

import com.muhd.bank_app_api.dto.LoginDTO;
import com.muhd.bank_app_api.dto.LoginResponseDTO;
import com.muhd.bank_app_api.dto.RegisterDTO;
import com.muhd.bank_app_api.dto.RegisterResponseDTO;
import com.muhd.bank_app_api.service.serviceImplementation.AuthServiceImple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthServiceImple authService;

    // 🧩 Register endpoint
    @PostMapping("/register")
    public RegisterResponseDTO register(@RequestBody RegisterDTO user) {
        return authService.createUser(user);
    }

    // 🔑 Login endpoint
    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginDTO user) {
        return authService.login(user);
    }
}
