package com.leviis.realworldexample.tag.application.port.inbound;

import com.leviis.realworldexample.tag.domain.Tag;
import java.util.List;

@FunctionalInterface
public interface FindAllTagsUseCase {
    List<Tag> execute();
}
