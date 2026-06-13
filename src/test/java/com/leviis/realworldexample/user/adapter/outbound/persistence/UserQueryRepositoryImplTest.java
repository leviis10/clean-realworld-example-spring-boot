package com.leviis.realworldexample.user.adapter.outbound.persistence;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

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
                    .thenReturn(Optional.of(UserEntity.builder().email(email).build()));

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
}
