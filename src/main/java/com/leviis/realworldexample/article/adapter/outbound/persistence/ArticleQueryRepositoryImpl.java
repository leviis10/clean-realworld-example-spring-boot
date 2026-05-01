package com.leviis.realworldexample.article.adapter.outbound.persistence;

import com.leviis.realworldexample.article.adapter.outbound.persistence.article.ArticleEntity;
import com.leviis.realworldexample.article.adapter.outbound.persistence.article.ArticleSpecification;
import com.leviis.realworldexample.article.adapter.outbound.persistence.article.JpaArticleRepository;
import com.leviis.realworldexample.article.application.port.outbound.ArticleQueryRepository;
import com.leviis.realworldexample.article.domain.Article;
import com.leviis.realworldexample.article.domain.Slug;
import com.leviis.realworldexample.user.adapter.outbound.persistence.user.UserEntity;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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
        final Specification<ArticleEntity> spec = ArticleSpecification.from(tag, author, favoriteBy);
        final PageRequest pageable =
                PageRequest.of(offset, limit, Sort.by("createdAt").descending());
        final Page<ArticleEntity> foundArticles = jpaArticleRepository.findAll(spec, pageable);
        return foundArticles.map(ArticleEntity::intoArticleDomain).toList();
    }

    @Override
    @Transactional
    public List<Article> findAllByAuthorIdIn(final List<Long> authorIds, final int offset, final int limit) {
        final List<UserEntity> authors = authorIds.stream()
                .map(id -> UserEntity.builder().id(id).build())
                .toList();
        final PageRequest pageable =
                PageRequest.of(offset, limit, Sort.by("createdAt").descending());
        final Page<ArticleEntity> foundArticles = jpaArticleRepository.findAllByAuthorIn(authors, pageable);
        return foundArticles.map(ArticleEntity::intoArticleDomain).toList();
    }

    @Override
    @Transactional
    public Optional<Article> getBySlug(final Slug slug) {
        final Optional<ArticleEntity> foundArticle = jpaArticleRepository.getBySlugAndSlugId(slug.value(), slug.id());
        return foundArticle.map(ArticleEntity::intoArticleDomain);
    }
}
