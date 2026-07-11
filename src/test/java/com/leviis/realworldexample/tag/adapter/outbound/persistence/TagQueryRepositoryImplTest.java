package com.leviis.realworldexample.tag.adapter.outbound.persistence;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.leviis.realworldexample.tag.adapter.outbound.persistence.tag.JpaTagRepository;
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
            when(jpaTagRepository.findAllById(any(Set.class))).thenReturn(List.of());

            Set<Long> tagIds = Set.of(1L, 2L, 3L);
            List<Tag> response = tagQueryRepository.findAllByIdIn(tagIds);

            assertNotNull(response);
        }
    }
}
