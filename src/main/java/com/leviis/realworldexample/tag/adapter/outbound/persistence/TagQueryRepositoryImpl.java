package com.leviis.realworldexample.tag.adapter.outbound.persistence;

import com.leviis.realworldexample.tag.adapter.outbound.persistence.tag.JpaTagRepository;
import com.leviis.realworldexample.tag.adapter.outbound.persistence.tag.TagEntity;
import com.leviis.realworldexample.tag.application.port.outbound.TagQueryRepository;
import com.leviis.realworldexample.tag.domain.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public final class TagQueryRepositoryImpl implements TagQueryRepository {
    private final JpaTagRepository jpaTagRepository;

    @Override
    public List<Tag> findAll() {
        var foundTags = jpaTagRepository.findAll();
        return foundTags.stream().map(TagEntity::intoTagDomain).toList();
    }
}
