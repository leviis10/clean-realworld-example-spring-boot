package com.leviis.realworldexample.user.application.exceptions;

import java.io.Serial;

public class AlreadyUnfollowException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -720048538767580237L;

    public AlreadyUnfollowException() {
        super("Already Unfollowed");
    }
}
