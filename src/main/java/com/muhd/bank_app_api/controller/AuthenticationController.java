package com.muhd.bank_app_api.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.muhd.bank_app_api.model.BankUser;
import com.muhd.bank_app_api.service.serviceImplementation.AuthServiceImple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.CrossOrigin;


@RestController()
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthenticationController {

    @Autowired
    private AuthServiceImple serviceImple;

    @GetMapping("/login")
    public String signIn(){
        return "hey";
    }

    @PostMapping("/register")
    public BankUser register(@RequestBody BankUser user){
        return serviceImple.createUser(user);
    }
    
}
