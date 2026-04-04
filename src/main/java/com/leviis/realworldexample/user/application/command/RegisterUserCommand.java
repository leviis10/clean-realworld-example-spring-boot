package com.leviis.realworldexample.user.application.command;

public record RegisterUserCommand(String email, String password, String username) {}
