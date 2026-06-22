package com.leviis.realworldexample.user.application.query.handler;

import com.leviis.realworldexample.user.application.exceptions.UserNotFoundException;
import com.leviis.realworldexample.user.application.port.outbound.UserQueryRepository;
import com.leviis.realworldexample.user.application.query.GetUserProfileQuery;
import com.leviis.realworldexample.user.application.readmodel.UserWithFollowStatus;
import com.leviis.realworldexample.user.domain.Email;
import com.leviis.realworldexample.user.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetUserProfileHandlerTest {
    @Mock
    private UserQueryRepository userQueryRepository;

    @InjectMocks
    private GetUserProfileHandler getUserProfileHandler;

    @Test
    public void execute_positiveCase_returnUserWithFollowStatus() {
        String searchedUsername = "test-searched-username";
        String searchedBio = "test-bio";
        String searchedImage = "test-image";
        when(userQueryRepository.findByUsername(anyString()))
                .thenReturn(Optional.of(User.builder()
                        .setId(1L)
                        .setEmail(new Email("test.searcheduser@example.com"))
                        .setUsername(searchedUsername)
                        .setBio(searchedBio)
                        .setImage(searchedImage)
                        .build()));
        when(userQueryRepository.getIsFollowing(anyLong(), anyLong())).thenReturn(true);

        GetUserProfileQuery query = GetUserProfileQuery.builder()
                .setUser(User.builder()
                        .setId(2L)
                        .setEmail(new Email("test@example.com"))
                        .setUsername("test-logged-in-username")
                        .build())
                .setUsername(searchedUsername)
                .build();
        UserWithFollowStatus response = getUserProfileHandler.execute(query);

        assertEquals(searchedUsername, response.username());
        assertEquals(searchedBio, response.bio());
        assertEquals(searchedImage, response.image());
        assertTrue(response.isFollowing());
    }

    @Test
    public void execute_userNotLoggedIn_returnUserWithFollowStatusFalse() {
        String searchedUsername = "test-searched-username";
        String searchedBio = "test-bio";
        String searchedImage = "test-image";
        when(userQueryRepository.findByUsername(anyString()))
                .thenReturn(Optional.of(User.builder()
                        .setId(1L)
                        .setEmail(new Email("test.searcheduser@example.com"))
                        .setUsername(searchedUsername)
                        .setBio(searchedBio)
                        .setImage(searchedImage)
                        .build()));

        GetUserProfileQuery query = GetUserProfileQuery.builder()
                .setUser(null)
                .setUsername(searchedUsername)
                .build();
        UserWithFollowStatus response = getUserProfileHandler.execute(query);

        assertEquals(searchedUsername, response.username());
        assertEquals(searchedBio, response.bio());
        assertEquals(searchedImage, response.image());
        assertFalse(response.isFollowing());
    }

    @Test
    public void execute_loggedInUserNotFollow_returnUserWithFollowStatusFalse() {
        String searchedUsername = "test-searched-username";
        String searchedBio = "test-bio";
        String searchedImage = "test-image";
        when(userQueryRepository.findByUsername(anyString()))
                .thenReturn(Optional.of(User.builder()
                        .setId(1L)
                        .setEmail(new Email("test.searcheduser@example.com"))
                        .setUsername(searchedUsername)
                        .setBio(searchedBio)
                        .setImage(searchedImage)
                        .build()));
        when(userQueryRepository.getIsFollowing(anyLong(), anyLong())).thenReturn(false);

        GetUserProfileQuery query = GetUserProfileQuery.builder()
                .setUser(User.builder()
                        .setId(2L)
                        .setEmail(new Email("test@example.com"))
                        .setUsername("test-logged-in-username")
                        .build())
                .setUsername(searchedUsername)
                .build();
        UserWithFollowStatus response = getUserProfileHandler.execute(query);

        assertEquals(searchedUsername, response.username());
        assertEquals(searchedBio, response.bio());
        assertEquals(searchedImage, response.image());
        assertFalse(response.isFollowing());
    }

    @Test
    public void execute_searchNonExistentUser_throwUserNotFoundException() {
        when(userQueryRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        GetUserProfileQuery query = GetUserProfileQuery.builder()
                .setUser(User.builder()
                        .setId(2L)
                        .setEmail(new Email("test@example.com"))
                        .setUsername("test-logged-in-username")
                        .build())
                .setUsername("test-searched-username")
                .build();
        assertThrows(UserNotFoundException.class, () -> getUserProfileHandler.execute(query));
    }
}
