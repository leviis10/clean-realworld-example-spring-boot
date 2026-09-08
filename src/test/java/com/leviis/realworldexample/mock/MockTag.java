package com.leviis.realworldexample.mock;

import com.leviis.realworldexample.tag.adapter.outbound.persistence.tag.TagEntity;
import com.leviis.realworldexample.tag.domain.Tag;
import java.time.OffsetDateTime;
import java.util.Map;

public class MockTag {
    public static final Tag TAG_1 = Tag.builder().setId(1L).setName("tag1").build();
    public static final Tag TAG_2 = Tag.builder().setId(2L).setName("tag2").build();
    public static final Tag TAG_3 = Tag.builder().setId(3L).setName("tag3").build();
    private static final TagEntity TAG_ENTITY_1 = TagEntity.builder()
            .id(TAG_1.id())
            .name(TAG_1.name())
            .createdAt(OffsetDateTime.now())
            .updatedAt(OffsetDateTime.now())
            .build();
    private static final TagEntity TAG_ENTITY_2 = TagEntity.builder()
            .id(TAG_2.id())
            .name(TAG_2.name())
            .createdAt(OffsetDateTime.now())
            .updatedAt(OffsetDateTime.now())
            .build();
    private static final TagEntity TAG_ENTITY_3 = TagEntity.builder()
            .id(TAG_3.id())
            .name(TAG_3.name())
            .createdAt(OffsetDateTime.now())
            .updatedAt(OffsetDateTime.now())
            .build();

    public static Map<Long, Tag> TAG_MAP = Map.of(
            TAG_1.id(), TAG_1,
            TAG_2.id(), TAG_2,
            TAG_3.id(), TAG_3);

    public static Map<Long, TagEntity> TAG_ENTITY_MAP = Map.of(
            TAG_ENTITY_1.getId(), TAG_ENTITY_1,
            TAG_ENTITY_2.getId(), TAG_ENTITY_2,
            TAG_ENTITY_3.getId(), TAG_ENTITY_3);
}
