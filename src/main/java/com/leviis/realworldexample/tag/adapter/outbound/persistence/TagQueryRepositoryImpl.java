package com.leviis.realworldexample.tag.adapter.outbound.persistence;

import com.leviis.realworldexample.tag.adapter.outbound.persistence.tag.JpaTagRepository;
import com.leviis.realworldexample.tag.adapter.outbound.persistence.tag.TagEntity;
import com.leviis.realworldexample.tag.application.port.outbound.TagQueryRepository;
import com.leviis.realworldexample.tag.domain.Tag;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class TagQueryRepositoryImpl implements TagQueryRepository {
    private final JpaTagRepository jpaTagRepository;

    @Override
    public List<Tag> findAll() {
        var foundTags = jpaTagRepository.findAll();
        return foundTags.stream().map(TagEntity::intoTagDomain).toList();
    }

    @Override
    public List<Tag> findAllByIdIn(final Set<Long> ids) {
        var foundTags = jpaTagRepository.findAllById(ids);
        return foundTags.stream().map(TagEntity::intoTagDomain).toList();
    }
}
