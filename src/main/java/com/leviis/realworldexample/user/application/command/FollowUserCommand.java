package com.leviis.realworldexample.user.application.command;

import com.leviis.realworldexample.user.domain.User;
import lombok.Builder;

@Builder(setterPrefix = "set")
public record FollowUserCommand(User follower, String followingUsername) {}
