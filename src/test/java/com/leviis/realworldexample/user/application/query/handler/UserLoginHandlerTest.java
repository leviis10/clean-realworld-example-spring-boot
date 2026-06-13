package com.leviis.realworldexample.user.application.query.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.leviis.realworldexample.user.application.command.UserWithToken;
import com.leviis.realworldexample.user.application.exceptions.IncorrectCredentialsException;
import com.leviis.realworldexample.user.application.port.outbound.PasswordService;
import com.leviis.realworldexample.user.application.port.outbound.TokenService;
import com.leviis.realworldexample.user.application.port.outbound.UserQueryRepository;
import com.leviis.realworldexample.user.application.query.UserLoginQuery;
import com.leviis.realworldexample.user.domain.Email;
import com.leviis.realworldexample.user.domain.User;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserLoginHandlerTest {
    @Mock
    private UserQueryRepository userQueryRepository;

    @Mock
    private PasswordService passwordService;

    @Mock
    private TokenService tokenService;

    @InjectMocks
    private UserLoginHandler userLoginHandler;

    @Test
    public void shouldSuccessWhenEverythingIsNormal() {
        Email email = new Email("test@example.com");
        String password = "Qwerty123!";
        String username = "test-username";
        String bio = "test-bio";
        String image = "test-image";
        String hashedPassword = "test-hashedPassword";
        UserLoginQuery query =
                UserLoginQuery.builder().setEmail(email).setPassword(password).build();
        User user = User.builder()
                .setEmail(email)
                .setUsername(username)
                .setBio(bio)
                .setImage(image)
                .setPassword(hashedPassword)
                .build();
        when(userQueryRepository.findByEmail(any(Email.class))).thenReturn(Optional.of(user));
        when(passwordService.compare(anyString(), anyString())).thenReturn(true);
        when(tokenService.generateToken(any(User.class))).thenReturn(anyString());

        UserWithToken response = userLoginHandler.execute(query);

        assertEquals(email.value(), response.email());
        assertEquals(username, response.username());
        assertEquals(bio, response.bio());
        assertEquals(image, response.image());
        assertNotNull(response.token());
    }

    @Test
    public void shouldThrowErrorWhenRequestedPasswordIsIncorrect() {
        Email email = new Email("test@example.com");
        String password = "Qwerty123!";
        String username = "test-username";
        String bio = "test-bio";
        String image = "test-image";
        String hashedPassword = "test-hashedPassword";
        UserLoginQuery query =
                UserLoginQuery.builder().setEmail(email).setPassword(password).build();
        User user = User.builder()
                .setEmail(email)
                .setUsername(username)
                .setBio(bio)
                .setImage(image)
                .setPassword(hashedPassword)
                .build();
        when(userQueryRepository.findByEmail(any(Email.class))).thenReturn(Optional.of(user));
        when(passwordService.compare(anyString(), anyString())).thenReturn(false);

        assertThrows(IncorrectCredentialsException.class, () -> userLoginHandler.execute(query));
    }

    @Test
    public void shouldThrowErrorWhenEmailIsNotExist() {
        Email email = new Email("test@example.com");
        String password = "Qwerty123!";
        UserLoginQuery query =
                UserLoginQuery.builder().setEmail(email).setPassword(password).build();

        when(userQueryRepository.findByEmail(any(Email.class))).thenReturn(Optional.empty());

        assertThrows(IncorrectCredentialsException.class, () -> userLoginHandler.execute(query));
    }
}
