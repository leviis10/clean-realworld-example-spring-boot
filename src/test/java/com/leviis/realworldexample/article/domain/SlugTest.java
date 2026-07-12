package com.leviis.realworldexample.article.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class SlugTest {
    @Nested
    class Constructor {
        @Test
        public void constructor_positiveCase_constructed() {
            new Slug("slug", UUID.randomUUID());
        }

        @Test
        public void constructor_valueIsNull_throwNullPointerException() {
            assertThrows(NullPointerException.class, () -> new Slug(null, UUID.randomUUID()));
        }

        @Test
        public void constructor_idIsNull_throwNullPointerException() {
            assertThrows(NullPointerException.class, () -> new Slug("slug", null));
        }
    }
}
