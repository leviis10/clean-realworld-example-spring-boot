package com.leviis.realworldexample.user.application.exceptions;

import java.io.Serial;

public class AlreadyFollowException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -4891006852322055187L;

    public AlreadyFollowException() {
        super("Already followed");
    }
}
