package com.leviis.realworldexample.user.domain;

public record RawPassword(String value) {
    private static final int MIN_LENGTH = 8;

    public RawPassword {
        if (value == null || value.length() < MIN_LENGTH) {
            throw new IllegalArgumentException("Invalid Password");
        }
    }
}
