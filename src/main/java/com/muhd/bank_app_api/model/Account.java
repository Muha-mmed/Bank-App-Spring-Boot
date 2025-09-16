package com.muhd.bank_app_api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long account_number;
    @Column
    private String account_holder_name;
    @Column
    private Double account_balance;
    public Account() {
    }
    public Account(Long account_number, String account_holder_name, Double account_balance) {
        this.account_number = account_number;
        this.account_holder_name = account_holder_name;
        this.account_balance = account_balance;
    }
    public Long getAccount_number() {
        return account_number;
    }
    public void setAccount_number(Long account_number) {
        this.account_number = account_number;
    }
    public String getAccount_holder_name() {
        return account_holder_name;
    }
    public void setAccount_holder_name(String account_holder_name) {
        this.account_holder_name = account_holder_name;
    }
    public Double getAccount_balance() {
        return account_balance;
    }
    public void setAccount_balance(Double account_balance) {
        this.account_balance = account_balance;
    }
    @Override
    public String toString() {
        return "Account [account_number=" + account_number + ", account_holder_name=" + account_holder_name
                + ", account_balance=" + account_balance + "]";
    }
}