package com.leviis.realworldexample.tag.adapter.outbound.persistence.tag;

import static org.junit.jupiter.api.Assertions.*;

import com.leviis.realworldexample.tag.domain.Tag;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class TagEntityTest {
    @Nested
    class Into {
        @Test
        public void into_tag_returnTag() {
            TagEntity tagEntity = TagEntity.builder().id(1L).name("test-tag").build();
            Tag response = tagEntity.into(Tag.class);

            assertEquals(tagEntity.getId(), response.id());
            assertEquals(tagEntity.getName(), response.name());
        }
    }
}
