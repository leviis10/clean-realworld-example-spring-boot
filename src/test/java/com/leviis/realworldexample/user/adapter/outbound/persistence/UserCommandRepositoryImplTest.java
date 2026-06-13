package com.leviis.realworldexample.user.adapter.outbound.persistence;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.leviis.realworldexample.user.adapter.outbound.persistence.follow.JpaFollowRepository;
import com.leviis.realworldexample.user.adapter.outbound.persistence.user.JpaUserRepository;
import com.leviis.realworldexample.user.adapter.outbound.persistence.user.UserEntity;
import com.leviis.realworldexample.user.domain.Email;
import com.leviis.realworldexample.user.domain.User;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserCommandRepositoryImplTest {
    @Mock
    private JpaUserRepository jpaUserRepository;

    @Mock
    private JpaFollowRepository jpaFollowRepository;

    @InjectMocks
    private UserCommandRepositoryImpl userCommandRepository;

    @Nested
    class Save {
        @Test
        public void save_positiveCase_returnUserDomain() {
            Email email = new Email("test@example.com");
            String username = "test-username";
            String bio = "test-bio";
            String image = "test-image";
            String password = "test-password";
            Long id = 1L;
            User user = User.builder()
                    .setEmail(email)
                    .setUsername(username)
                    .setBio(bio)
                    .setImage(image)
                    .setPassword(password)
                    .build();
            UserEntity userEntity = UserEntity.from(user.toBuilder().setId(id).build());
            when(jpaUserRepository.save(any(UserEntity.class))).thenReturn(userEntity);

            User response = userCommandRepository.save(user);

            assertNotNull(response.id());
            assertEquals(user.email(), response.email());
            assertEquals(user.username(), response.username());
            assertEquals(user.bio(), response.bio());
            assertEquals(user.image(), response.image());
            assertEquals(user.password(), response.password());
        }
    }
}
