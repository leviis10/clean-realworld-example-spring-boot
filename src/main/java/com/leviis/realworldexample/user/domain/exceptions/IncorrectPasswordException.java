package com.leviis.realworldexample.user.domain.exceptions;

import java.io.Serial;

public class IncorrectPasswordException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 6500336028298262701L;

    public IncorrectPasswordException() {
        super("Incorrect Password");
    }
}
