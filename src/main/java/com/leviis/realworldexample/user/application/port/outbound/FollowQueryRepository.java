package com.leviis.realworldexample.user.application.port.outbound;

import java.util.List;

public interface FollowQueryRepository {
    List<Long> findAllFollowingIdByFollowerId(Long followerId);
}
