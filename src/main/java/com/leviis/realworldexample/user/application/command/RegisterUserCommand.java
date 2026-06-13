package com.leviis.realworldexample.user.application.command;

import com.leviis.realworldexample.user.domain.Email;
import com.leviis.realworldexample.user.domain.RawPassword;
import lombok.Builder;

@Builder
public record RegisterUserCommand(Email email, RawPassword password, String username) {}
