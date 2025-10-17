package com.muhd.bank_app_api.service;

import java.util.Map;

import com.muhd.bank_app_api.model.BankUser;

public interface AuthService {
    public BankUser createUser (BankUser bUser);
    public Map<String, String> login (Map<String, String> user);
}
