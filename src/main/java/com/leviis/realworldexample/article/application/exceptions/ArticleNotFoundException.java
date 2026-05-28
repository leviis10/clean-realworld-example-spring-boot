package com.leviis.realworldexample.article.application.exceptions;

import com.leviis.realworldexample.article.domain.Slug;
import com.leviis.realworldexample.user.domain.User;
import java.io.Serial;

public class ArticleNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1498659817977864221L;

    public ArticleNotFoundException(final Slug slug) {
        super(String.format("No article found with slug of '%s-%s'", slug.value(), slug.id()));
    }

    public ArticleNotFoundException(final User user, final Slug slug) {
        super(String.format(
                "No article found with the author of '%s' and slug of '%s-%s'",
                user.username(), slug.value(), slug.id()));
    }
}
