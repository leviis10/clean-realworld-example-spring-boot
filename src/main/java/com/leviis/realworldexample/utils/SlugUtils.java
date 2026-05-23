package com.leviis.realworldexample.utils;

import java.util.UUID;

public final class SlugUtils {
    private static final int UUID_MIN_LENGTH = 36;

    private SlugUtils() {}

    public static String getTitleFrom(final String slug) {
        if (slug.length() <= UUID_MIN_LENGTH) {
            throw new IllegalArgumentException("Invalid slug");
        }

        return slug.substring(0, slug.length() - (UUID_MIN_LENGTH + 1));
    }

    public static UUID getIdFrom(final String slug) {
        if (slug.length() <= UUID_MIN_LENGTH) {
            throw new IllegalArgumentException("Invalid slug");
        }

        return UUID.fromString(slug.substring(slug.length() - UUID_MIN_LENGTH));
    }
}
