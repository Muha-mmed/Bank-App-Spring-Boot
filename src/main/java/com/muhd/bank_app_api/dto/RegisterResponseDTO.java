package com.muhd.bank_app_api.dto;

import com.muhd.bank_app_api.model.Account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterResponseDTO {
    private String firstName;
    private String lastName;
    private String email;
    private Account account;
}