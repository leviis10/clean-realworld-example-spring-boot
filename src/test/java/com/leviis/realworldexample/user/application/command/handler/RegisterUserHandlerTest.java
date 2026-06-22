package com.leviis.realworldexample.user.application.command.handler;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.leviis.realworldexample.infrastructure.exceptions.DuplicateResourceException;
import com.leviis.realworldexample.user.application.command.RegisterUserCommand;
import com.leviis.realworldexample.user.application.port.outbound.PasswordService;
import com.leviis.realworldexample.user.application.port.outbound.TokenService;
import com.leviis.realworldexample.user.application.port.outbound.UserCommandRepository;
import com.leviis.realworldexample.user.application.port.outbound.UserQueryRepository;
import com.leviis.realworldexample.user.application.readmodel.UserWithToken;
import com.leviis.realworldexample.user.domain.Email;
import com.leviis.realworldexample.user.domain.RawPassword;
import com.leviis.realworldexample.user.domain.User;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RegisterUserHandlerTest {
    @Mock
    private UserCommandRepository userCommandRepository;

    @Mock
    private UserQueryRepository userQueryRepository;

    @Mock
    private PasswordService passwordService;

    @Mock
    private TokenService tokenService;

    @InjectMocks
    private RegisterUserHandler registerUserHandler;

    @Test
    public void execute_positiveCase_returnUserWithToken() {
        Email email = new Email("test@example.com");
        RawPassword password = new RawPassword("Qwerty123!");
        String username = "test-username";
        RegisterUserCommand command = RegisterUserCommand.builder()
                .email(email)
                .password(password)
                .username(username)
                .build();

        long id = 1L;
        String hashedPassword = "test-hashedPassword";
        User user = User.builder()
                .setId(id)
                .setEmail(email)
                .setUsername(username)
                .setPassword(hashedPassword)
                .build();

        when(userQueryRepository.findByEmail(any(Email.class))).thenReturn(Optional.empty());
        when(userQueryRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        when(passwordService.hashPassword(any(RawPassword.class))).thenReturn(hashedPassword);
        when(userCommandRepository.save(any(User.class))).thenReturn(user);
        when(tokenService.generateToken(any(User.class))).thenReturn(anyString());

        UserWithToken response = registerUserHandler.execute(command);

        assertEquals(email.value(), response.email());
        assertEquals(username, response.username());
        assertNull(response.bio());
        assertNull(response.image());
        assertNotNull(response.token());
    }

    @Test
    public void execute_emailIsExist_throwDuplicateResourceException() {
        Email email = new Email("test@example.com");
        RawPassword password = new RawPassword("Qwerty123!");
        String username = "test-username";
        RegisterUserCommand command = RegisterUserCommand.builder()
                .email(email)
                .password(password)
                .username(username)
                .build();

        when(userQueryRepository.findByEmail(any(Email.class))).thenThrow(DuplicateResourceException.class);

        assertThrows(DuplicateResourceException.class, () -> registerUserHandler.execute(command));
    }

    @Test
    public void execute_usernameIsExist_throwDuplicateResourceException() {
        Email email = new Email("test@example.com");
        RawPassword password = new RawPassword("Qwerty123!");
        String username = "test-username";
        RegisterUserCommand command = RegisterUserCommand.builder()
                .email(email)
                .password(password)
                .username(username)
                .build();

        when(userQueryRepository.findByEmail(any(Email.class))).thenReturn(Optional.empty());
        when(userQueryRepository.findByUsername(anyString())).thenThrow(DuplicateResourceException.class);

        assertThrows(DuplicateResourceException.class, () -> registerUserHandler.execute(command));
    }
}
