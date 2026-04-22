package com.leviis.realworldexample.tag.application.port.outbound;

import com.leviis.realworldexample.tag.domain.Tag;
import java.util.List;
import java.util.Set;

public interface TagQueryRepository {
    List<Tag> findAll();

    List<Tag> findAllByIdIn(Set<Long> ids);
}
