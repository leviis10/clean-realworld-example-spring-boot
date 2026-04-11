package com.leviis.realworldexample.user.domain;

public record Email(String value) {
    public Email {
        if (value != null && (value.isBlank() || !value.contains("@"))) {
            throw new IllegalArgumentException("Invalid Email");
        }
    }
}
