package com.leviis.realworldexample.user.application.query;

import com.leviis.realworldexample.user.domain.Email;
import lombok.Builder;

@Builder(setterPrefix = "set")
public record UserLoginQuery(Email email, String password) {}
