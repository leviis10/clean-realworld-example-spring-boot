package com.leviis.realworldexample.user.application.command.handler;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import com.leviis.realworldexample.user.application.command.UpdateUserCommand;
import com.leviis.realworldexample.user.application.port.outbound.PasswordService;
import com.leviis.realworldexample.user.application.port.outbound.UserCommandRepository;
import com.leviis.realworldexample.user.domain.Email;
import com.leviis.realworldexample.user.domain.RawPassword;
import com.leviis.realworldexample.user.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UpdateUserHandlerTest {
    @Mock
    private UserCommandRepository userCommandRepository;

    @Mock
    private PasswordService passwordService;

    @InjectMocks
    private UpdateUserHandler updateUserHandler;

    @Test
    public void execute_positiveCase_returnUserDomain() {
        Email updatedEmail = new Email("testupdated@example.com");
        String updatedUsername = "test-username-updated";
        String updatedBio = "test-bio-updated";
        String updatedImage = "test-image-updated";
        UpdateUserCommand command = UpdateUserCommand.builder()
                .setId(1L)
                .setUsername("test-username")
                .setEmail(new Email("test@example.com"))
                .setPassword(new RawPassword("Qwerty123!"))
                .setImage("test-image")
                .setBio("test-bio")
                .build();
        when(userCommandRepository.updateById(anyLong(), any(User.class)))
                .thenReturn(User.builder()
                        .setId(1L)
                        .setEmail(updatedEmail)
                        .setUsername(updatedUsername)
                        .setBio(updatedBio)
                        .setImage(updatedImage)
                        .build());

        User response = updateUserHandler.execute(command);

        assertEquals(updatedEmail, response.email());
        assertEquals(updatedUsername, response.username());
        assertEquals(updatedImage, response.image());
        assertEquals(updatedBio, response.bio());
    }
}
