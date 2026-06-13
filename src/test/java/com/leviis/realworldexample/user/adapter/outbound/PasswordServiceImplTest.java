package com.leviis.realworldexample.user.adapter.outbound;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

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

    @Test
    public void shouldReturnTrueWhenComparingValidPassword() {
        String rawPassword = "rawPassword";
        String hashedPassword = "hashedPassword";
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);

        assertTrue(passwordService.compare(rawPassword, hashedPassword));
    }

    @Test
    public void shouldReturnFalseWhenComparingInvalidPassword() {
        String rawPassword = "rawPassword";
        String hashedPassword = "hashedPassword";
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        assertFalse(passwordService.compare(rawPassword, hashedPassword));
    }
}
