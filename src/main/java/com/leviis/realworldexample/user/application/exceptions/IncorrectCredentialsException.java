package com.leviis.realworldexample.user.application.exceptions;

import java.io.Serial;

public class IncorrectCredentialsException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -1218779221329169299L;

    public IncorrectCredentialsException() {
        super("Incorrect email or password");
    }
}
