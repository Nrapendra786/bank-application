package com.nrapendra.bank.customer;

public record CustomerRequest(
        String email,
        String password,
        String role) {
}
