package com.leviis.realworldexample.user.application.command;

import lombok.Builder;

@Builder(setterPrefix = "set")
public record UnfollowUserCommand(Long followerId, String followingUsername) {}
