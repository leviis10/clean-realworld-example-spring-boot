package com.leviis.realworldexample.user.adapter.outbound.persistence;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.leviis.realworldexample.user.adapter.outbound.persistence.follow.FollowEntity;
import com.leviis.realworldexample.user.adapter.outbound.persistence.follow.FollowId;
import com.leviis.realworldexample.user.adapter.outbound.persistence.follow.JpaFollowRepository;
import com.leviis.realworldexample.user.adapter.outbound.persistence.user.JpaUserRepository;
import com.leviis.realworldexample.user.adapter.outbound.persistence.user.UserEntity;
import com.leviis.realworldexample.user.domain.Email;
import com.leviis.realworldexample.user.domain.User;
import java.util.Optional;
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
            when(jpaFollowRepository.findById(any(FollowId.class)))
                    .thenReturn(Optional.empty());

            long followingId = 1L;
            long followerId = 2L;
            var response = userQueryRepository.getIsFollowing(followingId, followerId);

            assertFalse(response);
        }
    }
}
