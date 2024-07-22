package com.nrapendra.bank.message;

public record MessageRequest(
        String name,
        String email,
        String subject,
        String message) {

}
