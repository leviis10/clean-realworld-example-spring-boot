package com.leviis.realworldexample.user.adapter.outbound.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.when;

import com.leviis.realworldexample.user.adapter.outbound.persistence.follow.FollowEntity;
import com.leviis.realworldexample.user.adapter.outbound.persistence.follow.FollowId;
import com.leviis.realworldexample.user.adapter.outbound.persistence.follow.JpaFollowRepository;
import com.leviis.realworldexample.user.adapter.outbound.persistence.user.JpaUserRepository;
import com.leviis.realworldexample.user.adapter.outbound.persistence.user.UserEntity;
import com.leviis.realworldexample.user.domain.Email;
import com.leviis.realworldexample.user.domain.User;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserQueryRepositoryImplTest {
    @Mock
    private JpaUserRepository jpaUserRepository;

    @Mock
    private JpaFollowRepository jpaFollowRepository;

    @InjectMocks
    private UserQueryRepositoryImpl userQueryRepository;

    @Nested
    class FindByEmail {
        @Test
        public void findByEmail_emailExist_returnUser() {
            String email = "johndoe@example.com";
            when(jpaUserRepository.findByEmail(email))
                    .thenReturn(Optional.of(UserEntity.builder()
                            .id(1L)
                            .username("test-username")
                            .email(email)
                            .build()));

            Optional<User> response = userQueryRepository.findByEmail(new Email(email));

            assertTrue(response.isPresent());
        }

        @Test
        public void findByEmail_emailNotExist_returnEmpty() {
            String email = "johndoe@example.com";
            when(jpaUserRepository.findByEmail(email)).thenReturn(Optional.empty());

            Optional<User> response = userQueryRepository.findByEmail(new Email(email));

            assertTrue(response.isEmpty());
        }
    }

    @Nested
    class FindByUsername {
        @Test
        public void findByUsername_usernameExist_returnUser() {
            String username = "test-username";
            UserEntity user = UserEntity.builder()
                    .username("test-username")
                    .email("test@example.com")
                    .build();
            when(jpaUserRepository.findByUsername(username)).thenReturn(Optional.of(user));

            Optional<User> response = userQueryRepository.findByUsername(username);

            assertTrue(response.isPresent());
        }

        @Test
        public void findByUsername_emailNotExist_returnEmpty() {
            String username = "test-username";
            when(jpaUserRepository.findByUsername(username)).thenReturn(Optional.empty());

            Optional<User> response = userQueryRepository.findByUsername(username);

            assertTrue(response.isEmpty());
        }
    }

    @Nested
    class GetIsFollowing {
        @Test
        public void getIsFollowing_positiveCase_returnTrue() {
            when(jpaFollowRepository.findById(any(FollowId.class)))
                    .thenReturn(Optional.of(FollowEntity.builder().build()));

            long followingId = 1L;
            long followerId = 2L;
            var response = userQueryRepository.getIsFollowing(followingId, followerId);

            assertTrue(response);
        }

        @Test
        public void getIsFollowing_notFollowing_returnFalse() {
            when(jpaFollowRepository.findById(any(FollowId.class))).thenReturn(Optional.empty());

            long followingId = 1L;
            long followerId = 2L;
            var response = userQueryRepository.getIsFollowing(followingId, followerId);

            assertFalse(response);
        }
    }

    @Nested
    class FindByIds {
        @Test
        public void findByIds_positiveCase_returnListOfUsers() {
            long user1Id = 1L;
            long user2Id = 2L;
            when(jpaUserRepository.findAllById(anySet()))
                    .thenReturn(List.of(
                            UserEntity.builder()
                                    .id(user1Id)
                                    .email("user1@example.com")
                                    .username("user1")
                                    .build(),
                            UserEntity.builder()
                                    .id(user2Id)
                                    .email("user2@example.com")
                                    .username("user2")
                                    .build()));

            List<User> response = userQueryRepository.findByIds(Set.of(1L, 2L));

            assertFalse(response.isEmpty());
            assertEquals(2, response.size());
            assertEquals(user1Id, response.get(0).id());
            assertEquals(user2Id, response.get(1).id());
        }
    }

    @Nested
    class FindIsFollowingIn {
        @Test
        public void findIsFollowingIn_positiveCase_returnListOfUserFollowingId() {
            long followingId1 = 2L;
            long followingId2 = 3L;
            long followingId3 = 4L;
            FollowEntity followEntity1 = FollowEntity.builder()
                    .id(FollowId.builder().followingId(followingId1).build())
                    .build();
            FollowEntity followEntity2 = FollowEntity.builder()
                    .id(FollowId.builder().followingId(followingId2).build())
                    .build();
            when(jpaFollowRepository.findByFollowerAndFollowingIn(any(UserEntity.class), anyList()))
                    .thenReturn(List.of(followEntity1, followEntity2));

            User follower = User.builder()
                    .setId(1L)
                    .setEmail(new Email("user1@example.com"))
                    .setUsername("user1")
                    .build();
            User following1 = User.builder()
                    .setId(followingId1)
                    .setEmail(new Email("following1@example.com"))
                    .setUsername("following1")
                    .build();
            User following2 = User.builder()
                    .setId(followingId2)
                    .setEmail(new Email("following2@example.com"))
                    .setUsername("following2")
                    .build();
            User following3 = User.builder()
                    .setId(followingId3)
                    .setEmail(new Email("following3@example.com"))
                    .setUsername("following3")
                    .build();
            List<User> followings = List.of(following1, following2, following3);
            List<Long> response = userQueryRepository.findIsFollowingIn(follower, followings);

            assertFalse(response.isEmpty());
            assertTrue(response.containsAll(List.of(followingId1, followingId2)));
            assertFalse(response.contains(followingId3));
        }

        @Test
        public void findIsFollowingIn_followerIsNull_returnEmptyList() {
            long followingId1 = 2L;
            long followingId2 = 3L;
            long followingId3 = 4L;
            User follower = null;
            User following1 = User.builder()
                    .setId(followingId1)
                    .setEmail(new Email("following1@example.com"))
                    .setUsername("following1")
                    .build();
            User following2 = User.builder()
                    .setId(followingId2)
                    .setEmail(new Email("following2@example.com"))
                    .setUsername("following2")
                    .build();
            User following3 = User.builder()
                    .setId(followingId3)
                    .setEmail(new Email("following3@example.com"))
                    .setUsername("following3")
                    .build();
            List<User> followings = List.of(following1, following2, following3);
            List<Long> response = userQueryRepository.findIsFollowingIn(follower, followings);

            assertTrue(response.isEmpty());
        }
    }
}
