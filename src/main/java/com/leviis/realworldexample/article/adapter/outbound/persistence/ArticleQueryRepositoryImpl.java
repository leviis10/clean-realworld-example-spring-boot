package com.leviis.realworldexample.article.adapter.outbound.persistence;

import com.leviis.realworldexample.article.adapter.outbound.persistence.article.ArticleEntity;
import com.leviis.realworldexample.article.adapter.outbound.persistence.article.ArticleSpecification;
import com.leviis.realworldexample.article.adapter.outbound.persistence.article.JpaArticleRepository;
import com.leviis.realworldexample.article.application.port.outbound.ArticleQueryRepository;
import com.leviis.realworldexample.article.domain.Article;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Repository
public class ArticleQueryRepositoryImpl implements ArticleQueryRepository {
    private final JpaArticleRepository jpaArticleRepository;

    @Override
    @Transactional
    public List<Article> findAll(
            final String tag, final String author, final String favoriteBy, final int limit, final int offset) {
        var spec = ArticleSpecification.from(tag, author, favoriteBy);
        var pageable = PageRequest.of(offset, limit, Sort.by("createdAt").descending());
        var foundArticles = jpaArticleRepository.findAll(spec, pageable);
        return foundArticles.map(ArticleEntity::intoArticleDomain).toList();
    }
}
