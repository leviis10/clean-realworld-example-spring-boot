package com.leviis.realworldexample.user.adapter.outbound.persistence;

import com.leviis.realworldexample.user.adapter.outbound.persistence.follow.JpaFollowRepository;
import com.leviis.realworldexample.user.adapter.outbound.persistence.user.UserEntity;
import com.leviis.realworldexample.user.application.port.outbound.FollowQueryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class FollowQueryRepositoryImpl implements FollowQueryRepository {
    private final JpaFollowRepository jpaFollowRepository;

    @Override
    public List<Long> findAllFollowingIdByFollowerId(final Long followerId) {
        var result = jpaFollowRepository.findAllByFollower(
                UserEntity.builder().id(followerId).build());
        return result.stream()
                .map(followEntity -> followEntity.getId().getFollowingId())
                .toList();
    }
}
