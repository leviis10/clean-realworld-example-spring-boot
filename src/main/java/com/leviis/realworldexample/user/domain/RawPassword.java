package com.leviis.realworldexample.user.domain;

import java.util.regex.Pattern;

public record RawPassword(String value) {
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$");

    public RawPassword {
        if (value == null) {
            throw new IllegalArgumentException("Password cannot be null");
        }

        if (!PASSWORD_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException(
                    "Password must at least have 1 uppercase, 1 lowercase, 1 digit, 1 special characters, and at least"
                            + " 8 characters long");
        }
    }
}
