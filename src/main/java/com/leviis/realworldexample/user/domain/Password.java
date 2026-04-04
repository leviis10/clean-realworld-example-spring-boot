package com.leviis.realworldexample.user.domain;

public record Password(String value, String hashedPassword) {
    private static final int MIN_LENGTH = 8;

    public static PasswordBuilder builder() {
        return new PasswordBuilder();
    }

    public static final class PasswordBuilder {
        private String value;
        private String hashedPassword;

        public PasswordBuilder() {}

        public PasswordBuilder setValue(final String value) {
            if (value.length() < MIN_LENGTH) {
                throw new IllegalArgumentException("Password value must be at least 8 characters long");
            }
            this.value = value;
            return this;
        }

        public PasswordBuilder setHashedPassword(final String hashedPassword) {
            this.hashedPassword = hashedPassword;
            return this;
        }

        public Password build() {
            return new Password(this.value, this.hashedPassword);
        }
    }
}
