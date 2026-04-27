package com.leviis.realworldexample.article.adapter;

import com.leviis.realworldexample.article.application.port.inbound.FindAllArticleUseCase;
import com.leviis.realworldexample.article.application.port.inbound.FindAllFeedArticleUseCase;
import com.leviis.realworldexample.article.application.port.inbound.GetArticleUseCase;
import com.leviis.realworldexample.article.application.port.outbound.ArticleQueryRepository;
import com.leviis.realworldexample.article.application.port.outbound.UserFavoriteArticleQueryRepository;
import com.leviis.realworldexample.article.application.query.handler.FindAllArticleHandler;
import com.leviis.realworldexample.article.application.query.handler.FindAllFeedArticleHandler;
import com.leviis.realworldexample.article.application.query.handler.GetArticleHandler;
import com.leviis.realworldexample.tag.application.port.outbound.TagQueryRepository;
import com.leviis.realworldexample.user.application.port.outbound.FollowQueryRepository;
import com.leviis.realworldexample.user.application.port.outbound.UserQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class AdapterConfig {
    private final ArticleQueryRepository articleQueryRepository;
    private final TagQueryRepository tagQueryRepository;
    private final UserQueryRepository userQueryRepository;
    private final UserFavoriteArticleQueryRepository userFavoriteArticleQueryRepository;
    private final FollowQueryRepository followQueryRepository;

    @Bean
    public FindAllArticleUseCase findAllArticleUseCase() {
        return new FindAllArticleHandler(
                articleQueryRepository, tagQueryRepository, userQueryRepository, userFavoriteArticleQueryRepository);
    }

    @Bean
    public FindAllFeedArticleUseCase findAllFeedArticleUseCase() {
        return new FindAllFeedArticleHandler(
                articleQueryRepository,
                followQueryRepository,
                tagQueryRepository,
                userFavoriteArticleQueryRepository,
                userQueryRepository);
    }

    @Bean
    public GetArticleUseCase getArticleUseCase() {
        return new GetArticleHandler(
                articleQueryRepository,
                tagQueryRepository,
                userFavoriteArticleQueryRepository,
                userQueryRepository,
                followQueryRepository);
    }
}
