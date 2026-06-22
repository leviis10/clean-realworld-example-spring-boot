package com.leviis.realworldexample.user.application.command;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class UpdateUserCommandTest {
    @Nested
    class Constructor {
        @Test
        public void constructor_positiveCase_returnUpdateUserCommand() {}

        @Test
        public void constructor_idValueNull_throwNullPointerException() {}

        @Test
        public void constructor_emailValueNull_throwNullPointerException() {}

        @Test
        public void constructor_usernameValueNull_throwNullPointerException() {}
    }
}
