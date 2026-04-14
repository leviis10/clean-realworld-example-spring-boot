package com.leviis.realworldexample.tag.application.port.outbound;

import com.leviis.realworldexample.tag.domain.Tag;
import java.util.List;

public interface TagQueryRepository {
    List<Tag> findAll();
}
