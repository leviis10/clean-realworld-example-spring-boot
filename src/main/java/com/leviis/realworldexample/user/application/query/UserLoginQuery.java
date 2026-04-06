package com.leviis.realworldexample.user.application.query;

public final class UserLoginQuery {
    private String email;
    private String password;

    public UserLoginQuery(final String email, final String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
