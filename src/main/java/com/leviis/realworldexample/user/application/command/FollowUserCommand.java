package com.leviis.realworldexample.user.application.command;

import com.leviis.realworldexample.user.domain.User;

public record FollowUserCommand(User follower, User following) {
    public static FollowUserCommandBuilder builder() {
        return new FollowUserCommandBuilder();
    }

    public static final class FollowUserCommandBuilder {
        private User follower;
        private User following;

        public FollowUserCommandBuilder setFollower(final User follower) {
            this.follower = follower;
            return this;
        }

        public FollowUserCommandBuilder setFollowing(final User following) {
            this.following = following;
            return this;
        }

        public FollowUserCommand build() {
            return new FollowUserCommand(this.follower, this.following);
        }
    }
}
