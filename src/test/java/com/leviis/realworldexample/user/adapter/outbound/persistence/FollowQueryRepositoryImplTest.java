package com.leviis.realworldexample.user.adapter.outbound.persistence;

import com.leviis.realworldexample.user.adapter.outbound.persistence.follow.FollowEntity;
import com.leviis.realworldexample.user.adapter.outbound.persistence.follow.FollowId;
import com.leviis.realworldexample.user.adapter.outbound.persistence.follow.JpaFollowRepository;
import com.leviis.realworldexample.user.adapter.outbound.persistence.user.UserEntity;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FollowQueryRepositoryImplTest {
    @Mock
    private JpaFollowRepository jpaFollowRepository;

    @InjectMocks
    private FollowQueryRepositoryImpl followQueryRepository;

    @Nested
    class FindAllFollowingIdByFollowerId {
        @Test
        public void findAllFollowingIdByFollowerId_positiveCase_returnListOfFollowingId() {
            FollowEntity followEntity1 = FollowEntity.builder()
                    .id(FollowId.builder().followingId(2L).build())
                    .build();
            FollowEntity followEntity2 = FollowEntity.builder()
                    .id(FollowId.builder().followingId(3L).build())
                    .build();
            when(jpaFollowRepository.findAllByFollower(any(UserEntity.class)))
                    .thenReturn(List.of(followEntity1, followEntity2));

            long followerId = 1L;
            List<Long> response = followQueryRepository.findAllFollowingIdByFollowerId(followerId);

            assertEquals(2, response.size());
            assertEquals(followEntity1.getId().getFollowingId(), response.getFirst());
            assertEquals(followEntity2.getId().getFollowingId(), response.get(1));
        }
    }
}
