package com.leviis.realworldexample.user.application.command.handler;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.leviis.realworldexample.user.application.command.UnfollowUserCommand;
import com.leviis.realworldexample.user.application.exceptions.AlreadyUnfollowException;
import com.leviis.realworldexample.user.application.exceptions.SelfUnfollowException;
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
class UnfollowUserHandlerTest {
    @Mock
    private UserCommandRepository userCommandRepository;

    @Mock
    private UserQueryRepository userQueryRepository;

    @InjectMocks
    private UnfollowUserHandler unfollowUserHandler;

    @Test
    public void execute_positiveCase_returnUserWithFollowStatus() {
        Long followingId = 2L;
        String followingUsername = "test-following-username";
        String followingBio = "test-following-bio";
        String followingImage = "test-following-image";
        when(userQueryRepository.findByUsername(anyString()))
                .thenReturn(Optional.of(User.builder()
                        .setId(followingId)
                        .setUsername(followingUsername)
                        .setEmail(new Email("following@example.com"))
                        .setBio(followingBio)
                        .setImage(followingImage)
                        .build()));
        when(userQueryRepository.getIsFollowing(anyLong(), anyLong())).thenReturn(true);

        Long followerId = 1L;
        UnfollowUserCommand command = UnfollowUserCommand.builder()
                .setFollowerId(followerId)
                .setFollowingUsername(followingUsername)
                .build();
        UserWithFollowStatus response = unfollowUserHandler.execute(command);

        assertEquals(followingUsername, response.username());
        assertEquals(followingBio, response.bio());
        assertEquals(followingImage, response.image());
        assertFalse(response.isFollowing());
    }

    @Test
    public void execute_nullCommand_throwNullPointerException() {
        UnfollowUserCommand command = null;
        assertThrows(NullPointerException.class, () -> unfollowUserHandler.execute(command));
    }

    @Test
    public void execute_followingAndFollowerIdIsSame_throwSelfUnfollowException() {
        Long followingId = 1L;
        String followingUsername = "test-following-username";
        String followingBio = "test-following-bio";
        String followingImage = "test-following-image";
        when(userQueryRepository.findByUsername(anyString()))
                .thenReturn(Optional.of(User.builder()
                        .setId(followingId)
                        .setUsername(followingUsername)
                        .setEmail(new Email("following@example.com"))
                        .setBio(followingBio)
                        .setImage(followingImage)
                        .build()));

        Long followerId = 1L;
        UnfollowUserCommand command = UnfollowUserCommand.builder()
                .setFollowerId(followerId)
                .setFollowingUsername(followingUsername)
                .build();
        assertThrows(SelfUnfollowException.class, () -> unfollowUserHandler.execute(command));
    }

    @Test
    public void execute_unfollowNonExistentUser_throwUserNotFoundException() {
        String followingUsername = "test-following-username";
        when(userQueryRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        Long followerId = 1L;
        UnfollowUserCommand command = UnfollowUserCommand.builder()
                .setFollowerId(followerId)
                .setFollowingUsername(followingUsername)
                .build();
        assertThrows(UserNotFoundException.class, () -> unfollowUserHandler.execute(command));
    }

    @Test
    public void execute_userIsNotFollowing_throwAlreadyUnfollowException() {
        Long followingId = 2L;
        String followingUsername = "test-following-username";
        String followingBio = "test-following-bio";
        String followingImage = "test-following-image";
        when(userQueryRepository.findByUsername(anyString()))
                .thenReturn(Optional.of(User.builder()
                        .setId(followingId)
                        .setUsername(followingUsername)
                        .setEmail(new Email("following@example.com"))
                        .setBio(followingBio)
                        .setImage(followingImage)
                        .build()));
        when(userQueryRepository.getIsFollowing(anyLong(), anyLong())).thenReturn(false);

        Long followerId = 1L;
        UnfollowUserCommand command = UnfollowUserCommand.builder()
                .setFollowerId(followerId)
                .setFollowingUsername(followingUsername)
                .build();
        assertThrows(AlreadyUnfollowException.class, () -> unfollowUserHandler.execute(command));
    }
}
