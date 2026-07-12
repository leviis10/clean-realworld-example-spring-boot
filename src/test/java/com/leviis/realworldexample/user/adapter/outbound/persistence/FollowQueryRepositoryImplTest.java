package com.leviis.realworldexample.user.adapter.outbound.persistence;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.leviis.realworldexample.user.adapter.outbound.persistence.follow.FollowEntity;
import com.leviis.realworldexample.user.adapter.outbound.persistence.follow.FollowId;
import com.leviis.realworldexample.user.adapter.outbound.persistence.follow.JpaFollowRepository;
import com.leviis.realworldexample.user.adapter.outbound.persistence.user.UserEntity;
import com.leviis.realworldexample.user.domain.Email;
import com.leviis.realworldexample.user.domain.User;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

    @Nested
    class FindIsFollowing {
        @Test
        public void findIsFollowing_foundFollowData_returnTrue() {
            when(jpaFollowRepository.findById(any(FollowId.class)))
                    .thenReturn(Optional.of(FollowEntity.builder().build()));

            User follower = User.builder()
                    .setEmail(new Email("follower@example.com"))
                    .setUsername("follower")
                    .build();
            User following = User.builder()
                    .setEmail(new Email("following@example.com"))
                    .setUsername("following")
                    .build();
            boolean response = followQueryRepository.findIsFollowing(follower, following);

            assertTrue(response);
        }

        @Test
        public void findIsFollowing_followDataNotFound_returnFalse() {
            when(jpaFollowRepository.findById(any(FollowId.class))).thenReturn(Optional.empty());

            User follower = User.builder()
                    .setEmail(new Email("follower@example.com"))
                    .setUsername("follower")
                    .build();
            User following = User.builder()
                    .setEmail(new Email("following@example.com"))
                    .setUsername("following")
                    .build();
            boolean response = followQueryRepository.findIsFollowing(follower, following);

            assertFalse(response);
        }

        @Test
        public void findIsFollowing_followingIsNull_returnFalse() {
            User follower = User.builder()
                    .setEmail(new Email("follower@example.com"))
                    .setUsername("follower")
                    .build();
            boolean response = followQueryRepository.findIsFollowing(follower, null);

            assertFalse(response);
        }

        @Test
        public void findIsFollowing_followerIsNull_returnFalse() {
            User following = User.builder()
                    .setEmail(new Email("following@example.com"))
                    .setUsername("following")
                    .build();
            boolean response = followQueryRepository.findIsFollowing(null, following);

            assertFalse(response);
        }
    }
}
