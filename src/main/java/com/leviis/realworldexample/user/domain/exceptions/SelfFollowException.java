package com.leviis.realworldexample.user.domain.exceptions;

import java.io.Serial;

public class SelfFollowException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -8495985788997031863L;

    public SelfFollowException() {
        super("Cannot self follow");
    }
}
