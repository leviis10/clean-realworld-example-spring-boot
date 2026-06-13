package com.leviis.realworldexample.user.domain;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class EmailTest {
    @Test
    public void constructor_validEmail_constructed() {
        assertDoesNotThrow(() -> new Email("johndoe@example.com"));
    }

    @ParameterizedTest(name = "constructor_invalidEmail={0}_throwIllegalArgumentException")
    @ValueSource(
            strings = {"johndoeexample.com", "johndoe@", "johndoe@example", "johndoe@example.c", "@example.com", ""})
    public void constructor_invalidEmail_throwIllegalArgumentException(String invalidEmail) {
        assertThrows(IllegalArgumentException.class, () -> new Email(invalidEmail));
    }
}
