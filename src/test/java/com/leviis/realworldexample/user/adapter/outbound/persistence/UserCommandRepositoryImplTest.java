package com.leviis.realworldexample.user.adapter.outbound.persistence;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import com.leviis.realworldexample.user.adapter.outbound.persistence.follow.JpaFollowRepository;
import com.leviis.realworldexample.user.adapter.outbound.persistence.user.JpaUserRepository;
import com.leviis.realworldexample.user.adapter.outbound.persistence.user.UserEntity;
import com.leviis.realworldexample.user.application.exceptions.UserNotFoundException;
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
            when(jpaUserRepository.save(any(UserEntity.class)))
                    .thenReturn(UserEntity.from(user.toBuilder().setId(id).build()));

            User response = userCommandRepository.save(user);

            assertNotNull(response.id());
            assertEquals(user.email(), response.email());
            assertEquals(user.username(), response.username());
            assertEquals(user.bio(), response.bio());
            assertEquals(user.image(), response.image());
            assertEquals(user.password(), response.password());
        }
    }

    @Nested
    class UpdateById {
        @Test
        public void updateById_positiveCase_returnUserDomain() {
            long id = 1L;
            Email email = new Email("test@example.com");
            String username = "test-username";
            String bio = "test-bio";
            String image = "test-image";
            String password = "test-password";
            User updatedUserData = User.builder()
                    .setId(id)
                    .setEmail(email)
                    .setUsername(username)
                    .setBio(bio)
                    .setImage(image)
                    .setPassword(password)
                    .build();
            when(jpaUserRepository.findById(id))
                    .thenReturn(Optional.of(UserEntity.builder().build()));
            when(jpaUserRepository.save(any(UserEntity.class))).thenReturn(UserEntity.from(updatedUserData));

            User response = userCommandRepository.updateById(id, updatedUserData);

            assertEquals(email, response.email());
            assertEquals(username, response.username());
            assertEquals(bio, response.bio());
            assertEquals(image, response.image());
        }

        @Test
        public void updateById_updatingNonExistentUser_throwUserNotFoundException() {
            long id = 1L;
            User updatedUserData = User.builder()
                    .setId(id)
                    .setUsername("test-username")
                    .setEmail(new Email("test@example.com"))
                    .build();
            when(jpaUserRepository.findById(anyLong())).thenReturn(Optional.empty());

            assertThrows(UserNotFoundException.class, () -> userCommandRepository.updateById(id, updatedUserData));
        }
    }
}
