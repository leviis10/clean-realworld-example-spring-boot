package com.leviis.realworldexample.user.application.port.outbound;

import com.leviis.realworldexample.user.domain.User;
import java.util.List;

public interface FollowQueryRepository {
    List<Long> findAllFollowingIdByFollowerId(Long followerId);

    boolean findIsFollowing(User follower, User following);
}
