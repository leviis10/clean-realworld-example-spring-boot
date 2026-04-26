package com.leviis.realworldexample.user.adapter.outbound.persistence.follow;

import com.leviis.realworldexample.user.adapter.outbound.persistence.user.UserEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaFollowRepository extends JpaRepository<FollowEntity, FollowId> {
    List<FollowEntity> findByFollowerAndFollowingIn(UserEntity follower, List<UserEntity> followings);

    List<FollowEntity> findAllByFollower(UserEntity follower);
}
