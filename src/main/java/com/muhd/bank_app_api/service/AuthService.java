package com.muhd.bank_app_api.service;

import com.muhd.bank_app_api.dto.LoginDTO;
import com.muhd.bank_app_api.dto.LoginResponseDTO;
import com.muhd.bank_app_api.dto.RegisterDTO;
import com.muhd.bank_app_api.dto.RegisterResponseDTO;

public interface AuthService {
    public RegisterResponseDTO createUser (RegisterDTO userDto);
    public LoginResponseDTO login (LoginDTO user);
}