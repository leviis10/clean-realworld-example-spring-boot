package com.leviis.realworldexample.article.domain;

import java.util.Locale;
import java.util.UUID;

public record Slug(String value, UUID id) {
    public static Slug from(final String title, final UUID id) {
        if (id == null) {
            return from(title);
        }

        final String slug = getSlug(title);
        return new Slug(slug, id);
    }

    public static Slug from(final String title) {
        final UUID slugId = UUID.randomUUID();
        return from(title, slugId);
    }

    private static String getSlug(final String title) {
        return title.replace(" ", "-").toLowerCase(Locale.getDefault());
    }
}
