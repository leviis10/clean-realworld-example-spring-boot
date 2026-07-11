package com.leviis.realworldexample.article.adapter.outbound.persistence;

import com.leviis.realworldexample.article.adapter.outbound.persistence.article.ArticleEntity;
import com.leviis.realworldexample.article.adapter.outbound.persistence.article.JpaArticleRepository;
import com.leviis.realworldexample.article.adapter.outbound.persistence.articletag.ArticleTagEntity;
import com.leviis.realworldexample.article.adapter.outbound.persistence.articletag.JpaArticleTagRepository;
import com.leviis.realworldexample.article.application.port.outbound.ArticleCommandRepository;
import com.leviis.realworldexample.article.domain.Article;
import com.leviis.realworldexample.article.domain.Slug;
import com.leviis.realworldexample.tag.domain.Tag;
import com.leviis.realworldexample.user.adapter.outbound.persistence.user.UserEntity;
import com.leviis.realworldexample.user.domain.User;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Repository
public class ArticleCommandRepositoryImpl implements ArticleCommandRepository {
    private final JpaArticleRepository jpaArticleRepository;
    private final JpaArticleTagRepository jpaArticleTagRepository;

    @Override
    public Article create(final Article article, final Map<Long, Tag> tagMap) {
        final ArticleEntity newArticle = jpaArticleRepository.save(ArticleEntity.from(article, tagMap));
        jpaArticleTagRepository.saveAll(getArticleTags(newArticle, tagMap));
        return newArticle.into(Article.class);
    }

    @Override
    @Transactional
    public Article save(final Article article) {
        final ArticleEntity savedArticle = jpaArticleRepository.save(ArticleEntity.from(article));
        return savedArticle.into(Article.class);
    }

    @Override
    @Transactional
    public void deleteByAuthorAndSlug(final User author, final Slug slug) {
        jpaArticleRepository.deleteByAuthorAndSlugAndSlugId(UserEntity.from(author), slug.value(), slug.id());
    }

    private static List<ArticleTagEntity> getArticleTags(
            final ArticleEntity articleEntity, final Map<Long, Tag> tagMap) {
        return tagMap.keySet().stream()
                .map(tagId -> ArticleTagEntity.from(articleEntity, tagMap.get(tagId)))
                .toList();
    }
}
