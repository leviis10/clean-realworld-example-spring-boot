package com.leviis.realworldexample.user.domain;

public final class Password {
    private static final int MIN_LENGTH = 8;

    private final String value;
    private final String hashedPassword;

    public Password(final String value) {
        this.value = value;
        this.hashedPassword = null;
    }

    public Password(final String value, final String hashedPassword) {
        this.value = value;
        this.hashedPassword = hashedPassword;
    }

    public String getValue() {
        return value;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public static PasswordBuilder builder() {
        return new PasswordBuilder();
    }

    public static final class PasswordBuilder {
        private String value;
        private String hashedPassword;

        public PasswordBuilder() {}

        public PasswordBuilder setValue(final String value) {
            this.value = value;
            return this;
        }

        public PasswordBuilder setHashedPassword(final String hashedPassword) {
            this.hashedPassword = hashedPassword;
            return this;
        }

        public Password build() {
            if (this.value != null && this.value.length() < MIN_LENGTH) {
                throw new IllegalArgumentException("Password value must be at least 8 characters long");
            }
            return new Password(this.value, this.hashedPassword);
        }
    }
}
