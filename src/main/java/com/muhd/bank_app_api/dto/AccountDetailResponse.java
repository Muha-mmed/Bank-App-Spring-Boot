package com.muhd.bank_app_api.dto;

import lombok.Data;

@Data
public class AccountDetailResponse {

    private String firstName;

    private String lastName;

    private String accountNumber;

    private Double accountBalance;
}
