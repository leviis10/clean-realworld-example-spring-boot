package com.leviis.realworldexample.utils;

import java.util.UUID;

public final class SlugUtils {
    public static final int UUID_MIN_LENGTH = 36;

    private SlugUtils() {}

    public static String getTitleFrom(final String slug, final int slugLength) {
        return slug.substring(0, slugLength - 1);
    }

    public static String getTitleFrom(final String slug) {
        final int slugLength = slug.length() - UUID_MIN_LENGTH;
        return getTitleFrom(slug, slugLength);
    }

    public static UUID getIdFrom(final String slug, final int slugLength) {
        return UUID.fromString(slug.substring(slugLength));
    }

    public static UUID getIdFrom(final String slug) {
        final int slugLength = slug.length() - UUID_MIN_LENGTH;
        return getIdFrom(slug, slugLength);
    }
}
