package com.leviis.realworldexample.tag.adapter;

import com.leviis.realworldexample.tag.application.port.inbound.FindAllTagsUseCase;
import com.leviis.realworldexample.tag.application.port.outbound.TagQueryRepository;
import com.leviis.realworldexample.tag.application.query.handler.FindAllTagsHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class TagAdapterConfig {
    private final TagQueryRepository tagQueryRepository;

    @Bean
    public FindAllTagsUseCase getAllTagsUseCase() {
        return new FindAllTagsHandler(tagQueryRepository);
    }
}
