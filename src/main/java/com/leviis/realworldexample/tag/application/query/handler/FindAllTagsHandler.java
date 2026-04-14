package com.leviis.realworldexample.tag.application.query.handler;

import com.leviis.realworldexample.tag.application.port.inbound.FindAllTagsUseCase;
import com.leviis.realworldexample.tag.application.port.outbound.TagQueryRepository;
import com.leviis.realworldexample.tag.domain.Tag;
import java.util.List;

public final class FindAllTagsHandler implements FindAllTagsUseCase {
    private final TagQueryRepository tagQueryRepository;

    public FindAllTagsHandler(final TagQueryRepository tagQueryRepository) {
        this.tagQueryRepository = tagQueryRepository;
    }

    @Override
    public List<Tag> execute() {
        return tagQueryRepository.findAll();
    }
}
