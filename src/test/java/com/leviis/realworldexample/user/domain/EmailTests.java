package com.leviis.realworldexample.user.domain;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class EmailTests {
    @Test
    @DisplayName("Should not throw when given a valid email")
    void shouldNotThrowWhenEmailIsValid() {
        assertDoesNotThrow(() -> new Email("johndoe@example.com"));
    }

    @ParameterizedTest(name = "Should throw when email is \"{0}\"")
    @ValueSource(
            strings = {"johndoeexample.com", "johndoe@", "johndoe@example", "johndoe@example.c", "@example.com", ""})
    void shouldThrowWhenEmailIsInvalid(String invalidEmail) {
        assertThrows(IllegalArgumentException.class, () -> new Email(invalidEmail));
    }
}
