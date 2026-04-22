package com.leviis.realworldexample.user.application.command;

public record UnfollowUserCommand(Long followerId, Long followingId) {
    public static UnfollowUserCommandBuilder builder() {
        return new UnfollowUserCommandBuilder();
    }

    public static final class UnfollowUserCommandBuilder {
        private Long followerId;
        private Long followingId;

        public UnfollowUserCommandBuilder setFollowerId(final Long followerId) {
            this.followerId = followerId;
            return this;
        }

        public UnfollowUserCommandBuilder setFollowingId(final Long followingId) {
            this.followingId = followingId;
            return this;
        }

        public UnfollowUserCommand build() {
            return new UnfollowUserCommand(this.followerId, this.followingId);
        }
    }
}
