package com.leviis.realworldexample.user.application.port.outbound;

import com.leviis.realworldexample.user.domain.User;
import java.util.List;
import java.util.Set;

public interface FollowQueryRepository {
    List<Long> findAllFollowingIdByFollowerId(Long followerId);

    boolean findIsFollowing(User follower, User following);

    List<Long> findByFollowerAndFollowingIdIn(Long followerId, Set<Long> followingIds);
}
