package com.leviis.realworldexample.user.domain;

public final class User {
    private Long id;
    private final Email email;
    private final String username;
    private String bio;
    private String image;
    private final Password password;
    private String token;

    public User(final Email email, final String username, final Password password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public User(
            final Long id,
            final Email email,
            final String username,
            final String bio,
            final String image,
            final Password password) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.bio = bio;
        this.image = image;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email.value();
    }

    public String getUsername() {
        return username;
    }

    public String getBio() {
        return bio;
    }

    public String getImage() {
        return image;
    }

    public String getToken() {
        return token;
    }

    public String getPassword() {
        return password.getHashedPassword();
    }

    public String getHashedPassword() {
        return password.getHashedPassword();
    }

    public void setToken(final String token) {
        this.token = token;
    }
}
