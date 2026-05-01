package com.leviis.realworldexample.article.domain;

import java.util.Locale;
import java.util.UUID;

public record Slug(String value, UUID id) {
    public static Slug from(final String title) {
        final String slug = getSlug(title);
        final UUID slugId = UUID.randomUUID();
        return new Slug(slug, slugId);
    }

    private static String getSlug(final String title) {
        return title.replace(" ", "-").toLowerCase(Locale.getDefault());
    }
}
