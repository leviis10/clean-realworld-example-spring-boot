package com.leviis.realworldexample.user.application.port.inbound;

public interface UnfollowUserUseCase {
    boolean execute(Long followerId, Long followingId);
}
