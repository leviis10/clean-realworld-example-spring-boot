package com.leviis.realworldexample.article.domain;

import java.util.Locale;
import java.util.Objects;
import java.util.UUID;
import org.jspecify.annotations.NonNull;

public record Slug(@NonNull String value, @NonNull UUID id) {
    public Slug {
        Objects.requireNonNull(value);
        Objects.requireNonNull(id);
    }

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

    @NonNull
    @Override
    public String toString() {
        return String.format("%s-%s", value, id);
    }

    private static String getSlug(final String title) {
        return title.replace(" ", "-").toLowerCase(Locale.getDefault());
    }
}
