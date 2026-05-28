package com.leviis.realworldexample.article.application.exceptions;

import java.io.Serial;

public class AuthorNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -4900046395657331353L;

    public AuthorNotFoundException(final long authorId) {
        super(String.format("Author with id of '%s' not found", authorId));
    }
}
