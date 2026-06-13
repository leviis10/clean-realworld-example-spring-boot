package com.leviis.realworldexample.user.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class RawPasswordTest {
    @Test
    public void constructor_strongPassword_constructed() {
        String password = "Qwerty123!";

        RawPassword response = new RawPassword(password);

        assertEquals(password, response.value());
    }

    @ParameterizedTest(name = "constructor_weakPassword={0}_throwIllegalArgumentException")
    @ValueSource(strings = {"123456", "a", "", "qwerty123!", "Qwerty1234", "Qwertyuio!", "QWERTY123!"})
    public void constructor_weakPassword_throwIllegalArgumentException(String password) {
        assertThrows(IllegalArgumentException.class, () -> new RawPassword(password));
    }

    @Test
    public void constructor_nullValue_throwIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new RawPassword(null));
    }
}
