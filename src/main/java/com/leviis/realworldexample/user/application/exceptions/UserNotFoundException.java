package com.leviis.realworldexample.user.application.exceptions;

import java.io.Serial;

public class UserNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -8046400642759031769L;

    public UserNotFoundException(final String username) {
        super(String.format("User with the username of '%s' not found", username));
    }
}
