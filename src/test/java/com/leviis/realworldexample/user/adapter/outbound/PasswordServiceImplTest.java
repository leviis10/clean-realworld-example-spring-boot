package com.leviis.realworldexample.user.adapter.outbound;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.leviis.realworldexample.user.domain.RawPassword;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class PasswordServiceImplTest {
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private PasswordServiceImpl passwordService;

    @Nested
    class Compare {
        @Test
        public void compare_comparingValidPassword_returnTrue() {
            String rawPassword = "rawPassword";
            String hashedPassword = "hashedPassword";
            when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);

            assertTrue(passwordService.compare(rawPassword, hashedPassword));
        }

        @Test
        public void compare_comparingInvalidPassword_returnFalse() {
            String rawPassword = "rawPassword";
            String hashedPassword = "hashedPassword";
            when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

            assertFalse(passwordService.compare(rawPassword, hashedPassword));
        }
    }

    @Nested
    class HashPassword {
        @Test
        public void hashPassword_positiveCase_returnHashedPassword() {
            RawPassword rawPassword = new RawPassword("Qwerty123!");
            when(passwordEncoder.encode(anyString())).thenReturn(anyString());

            String response = passwordService.hashPassword(rawPassword);

            assertNotEquals(rawPassword.value(), response);
        }
    }
}
