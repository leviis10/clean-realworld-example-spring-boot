package com.leviis.realworldexample.user.adapter.inbound.http.dto.request.constants;

public final class ErrorMessages {
    public static final String INVALID_EMAIL_VALIDATION = "That email doesn't look quite right.";
    public static final String NULL_EMAIL_VALIDATION = "An email address is required, but none was provided.";
    public static final String MIN_PASSWORD_VALIDATION = "Your password needs at least 8 characters to be secure.";
    public static final String NULL_PASSWORD_VALIDATION = "A password is required, but it's missing.";
    public static final String BLANK_USERNAME_VALIDATION = "Your username can't be empty, give it something unique!";
    public static final String NULL_USERNAME_VALIDATION = "A username is required, but none was provided.";

    private ErrorMessages() {}
}
