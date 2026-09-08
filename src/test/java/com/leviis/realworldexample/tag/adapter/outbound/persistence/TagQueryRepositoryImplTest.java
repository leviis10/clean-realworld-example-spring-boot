package com.leviis.realworldexample.tag.adapter.outbound.persistence;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.leviis.realworldexample.tag.adapter.outbound.persistence.tag.JpaTagRepository;
import com.leviis.realworldexample.tag.adapter.outbound.persistence.tag.TagEntity;
import com.leviis.realworldexample.tag.domain.Tag;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TagQueryRepositoryImplTest {
    @Mock
    private JpaTagRepository jpaTagRepository;

    @InjectMocks
    private TagQueryRepositoryImpl tagQueryRepository;

    @Nested
    class FindAllByIdIn {
        @Test
        public void findAllByIdIn_positiveCase_returnListOfTag() {
            when(jpaTagRepository.findAllById(anySet())).thenReturn(List.of());

            Set<Long> tagIds = Set.of(1L, 2L, 3L);
            List<Tag> response = tagQueryRepository.findAllByIdIn(tagIds);

            assertNotNull(response);
        }
    }

    @Nested
    class FindAllByNameIn {
        @Test
        public void findAllByNameIn_positiveCase_returnListOfTag() {
            TagEntity tagEntity1 = TagEntity.builder().id(1L).name("tag1").build();
            TagEntity tagEntity2 = TagEntity.builder().id(2L).name("tag2").build();
            when(jpaTagRepository.findAllByNameIn(anySet())).thenReturn(List.of(tagEntity1, tagEntity2));

            Set<String> tagNames = Set.of(tagEntity1.getName(), tagEntity2.getName());
            List<Tag> response = tagQueryRepository.findAllByNameIn(tagNames);

            assertNotNull(response);
            assertEquals(2, response.size());

            Tag tag1 = response.getFirst();
            assertEquals(tagEntity1.getId(), tag1.id());
            assertEquals(tagEntity1.getName(), tag1.name());

            Tag tag2 = response.getLast();
            assertEquals(tagEntity2.getId(), tag2.id());
            assertEquals(tagEntity2.getName(), tag2.name());
        }
    }
}
