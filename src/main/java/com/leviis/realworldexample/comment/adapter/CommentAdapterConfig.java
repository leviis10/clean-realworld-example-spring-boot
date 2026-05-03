package com.leviis.realworldexample.comment.adapter;

import com.leviis.realworldexample.article.application.port.outbound.ArticleQueryRepository;
import com.leviis.realworldexample.comment.application.command.handler.CreateCommentHandler;
import com.leviis.realworldexample.comment.application.port.inbound.CreateCommentUseCase;
import com.leviis.realworldexample.comment.application.port.outbound.CommentCommandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class CommentAdapterConfig {
    private final CommentCommandRepository commentCommandRepository;
    private final ArticleQueryRepository articleQueryRepository;

    @Bean
    public CreateCommentUseCase createCommentUseCase() {
        return new CreateCommentHandler(commentCommandRepository, articleQueryRepository);
    }
}
