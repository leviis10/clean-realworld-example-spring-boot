package com.leviis.realworldexample.infrastructure.constants;

@SuppressWarnings("PMD.DataClass")
public final class PaginationConstants {
    public static final int DEFAULT_LIMIT = 10;
    public static final int DEFAULT_OFFSET = 0;

    public static final int MINIMUM_ALLOWED_LIMIT = 1;
    public static final int MINIMUM_ALLOWED_OFFSET = 0;

    private PaginationConstants() {}
}
