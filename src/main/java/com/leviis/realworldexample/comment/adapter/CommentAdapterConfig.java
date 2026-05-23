package com.leviis.realworldexample.comment.adapter;

import com.leviis.realworldexample.article.application.port.outbound.ArticleQueryRepository;
import com.leviis.realworldexample.comment.application.command.handler.CreateCommentHandler;
import com.leviis.realworldexample.comment.application.port.inbound.CreateCommentUseCase;
import com.leviis.realworldexample.comment.application.port.inbound.FindAllCommentUseCase;
import com.leviis.realworldexample.comment.application.port.outbound.CommentCommandRepository;
import com.leviis.realworldexample.comment.application.port.outbound.CommentQueryRepository;
import com.leviis.realworldexample.comment.application.query.handler.FindAllCommentHandler;
import com.leviis.realworldexample.user.application.port.outbound.FollowQueryRepository;
import com.leviis.realworldexample.user.application.port.outbound.UserQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class CommentAdapterConfig {
    private final CommentCommandRepository commentCommandRepository;
    private final ArticleQueryRepository articleQueryRepository;
    private final CommentQueryRepository commentQueryRepository;
    private final UserQueryRepository userQueryRepository;
    private final FollowQueryRepository followQueryRepository;

    @Bean
    public CreateCommentUseCase createCommentUseCase() {
        return new CreateCommentHandler(commentCommandRepository, articleQueryRepository);
    }

    @Bean
    public FindAllCommentUseCase findAllCommentUseCase() {
        return new FindAllCommentHandler(commentQueryRepository, userQueryRepository, followQueryRepository);
    }
}
