package com.muhd.bank_app_api.dto;

import lombok.Data;

@Data
public class AccountDetailResponseDTO {

    private String email;

    private String firstName;

    private String lastName;

    private String accountNumber;

    private Double accountBalance;
}
