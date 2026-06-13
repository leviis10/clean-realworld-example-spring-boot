package com.leviis.realworldexample.user.domain;

import java.util.regex.Pattern;
import org.jspecify.annotations.NonNull;

public record Email(@NonNull String value) {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[^@\\s]+@[^@\\s]+\\.\\S{2,}$");

    public Email {
        if (value.isBlank()) {
            throw new IllegalArgumentException("Email value can't be empty string");
        }
        if (!EMAIL_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("Invalid email format: " + value);
        }
    }
}
