package com.leviis.realworldexample.user.application.command;

public record RegisterUserCommand(String email, String password, String username) {
    public static RegisterUserCommandBuilder builder() {
        return new RegisterUserCommandBuilder();
    }

    public static final class RegisterUserCommandBuilder {
        private String email;
        private String password;
        private String username;

        public RegisterUserCommandBuilder setEmail(final String email) {
            this.email = email;
            return this;
        }

        public RegisterUserCommandBuilder setPassword(final String password) {
            this.password = password;
            return this;
        }

        public RegisterUserCommandBuilder setUsername(final String username) {
            this.username = username;
            return this;
        }

        public RegisterUserCommand build() {
            return new RegisterUserCommand(this.email, this.password, this.username);
        }
    }
}
