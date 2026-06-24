package com.leviis.realworldexample.user.application.command.handler;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.leviis.realworldexample.user.application.command.FollowUserCommand;
import com.leviis.realworldexample.user.application.exceptions.AlreadyFollowException;
import com.leviis.realworldexample.user.application.exceptions.SelfFollowException;
import com.leviis.realworldexample.user.application.exceptions.UserNotFoundException;
import com.leviis.realworldexample.user.application.port.outbound.UserCommandRepository;
import com.leviis.realworldexample.user.application.port.outbound.UserQueryRepository;
import com.leviis.realworldexample.user.application.readmodel.UserWithFollowStatus;
import com.leviis.realworldexample.user.domain.Email;
import com.leviis.realworldexample.user.domain.User;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FollowUserHandlerTest {
    @Mock
    private UserCommandRepository userCommandRepository;

    @Mock
    private UserQueryRepository userQueryRepository;

    @InjectMocks
    private FollowUserHandler followUserHandler;

    @Test
    public void execute_positiveCase_returnUserWithFollowStatus() {
        String followingUsername = "test-following";
        String followingBio = "test-bio";
        String followingImage = "test-image";

        when(userQueryRepository.findByUsername(anyString()))
                .thenReturn(Optional.of(User.builder()
                        .setId(2L)
                        .setUsername(followingUsername)
                        .setEmail(new Email("following@example.com"))
                        .setBio(followingBio)
                        .setImage(followingImage)
                        .build()));
        when(userQueryRepository.getIsFollowing(anyLong(), anyLong())).thenReturn(false);

        FollowUserCommand command = FollowUserCommand.builder()
                .setFollower(User.builder()
                        .setId(1L)
                        .setUsername("test-follower")
                        .setEmail(new Email("follower@example.com"))
                        .build())
                .setFollowingUsername(followingUsername)
                .build();
        UserWithFollowStatus response = followUserHandler.execute(command);

        assertEquals(followingUsername, response.username());
        assertEquals(followingBio, response.bio());
        assertEquals(followingImage, response.image());
        assertTrue(response.isFollowing());
    }

    @Test
    public void execute_commandIsNull_throwNullPointerException() {
        assertThrows(NullPointerException.class, () -> followUserHandler.execute(null));
    }

    @Test
    public void execute_alreadyFollow_throwAlreadyFollowException() {
        String followingUsername = "test-following";

        when(userQueryRepository.findByUsername(anyString()))
                .thenReturn(Optional.of(User.builder()
                        .setId(2L)
                        .setUsername(followingUsername)
                        .setEmail(new Email("following@example.com"))
                        .setBio("test-bio")
                        .setImage("test-image")
                        .build()));
        when(userQueryRepository.getIsFollowing(anyLong(), anyLong())).thenReturn(true);

        FollowUserCommand command = FollowUserCommand.builder()
                .setFollower(User.builder()
                        .setId(1L)
                        .setUsername("test-follower")
                        .setEmail(new Email("follower@example.com"))
                        .build())
                .setFollowingUsername(followingUsername)
                .build();

        assertThrows(AlreadyFollowException.class, () -> followUserHandler.execute(command));
    }

    @Test
    public void execute_followerAndFollowingIdIsSame_throwSelfFollowException() {
        long followId = 1L;
        String followingUsername = "test-following";

        when(userQueryRepository.findByUsername(anyString()))
                .thenReturn(Optional.of(User.builder()
                        .setId(followId)
                        .setUsername(followingUsername)
                        .setEmail(new Email("following@example.com"))
                        .setBio("test-bio")
                        .setImage("test-image")
                        .build()));

        FollowUserCommand command = FollowUserCommand.builder()
                .setFollower(User.builder()
                        .setId(followId)
                        .setUsername("test-follower")
                        .setEmail(new Email("follower@example.com"))
                        .build())
                .setFollowingUsername(followingUsername)
                .build();

        assertThrows(SelfFollowException.class, () -> followUserHandler.execute(command));
    }

    @Test
    public void execute_followingUsernameIsEmpty_throwUserNotFoundException() {
        when(userQueryRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        FollowUserCommand command = FollowUserCommand.builder()
                .setFollower(User.builder()
                        .setId(1L)
                        .setUsername("test-follower")
                        .setEmail(new Email("follower@example.com"))
                        .build())
                .setFollowingUsername("test-following")
                .build();

        assertThrows(UserNotFoundException.class, () -> followUserHandler.execute(command));
    }
}
