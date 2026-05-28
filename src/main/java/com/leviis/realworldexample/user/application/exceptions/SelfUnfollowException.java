package com.leviis.realworldexample.user.application.exceptions;

import java.io.Serial;

public class SelfUnfollowException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -1874879949998333675L;

    public SelfUnfollowException() {
        super("Cannot self unfollow");
    }
}
