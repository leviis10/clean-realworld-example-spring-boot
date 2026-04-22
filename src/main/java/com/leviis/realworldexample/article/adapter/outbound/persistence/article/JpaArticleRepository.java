package com.leviis.realworldexample.article.adapter.outbound.persistence.article;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaArticleRepository
        extends JpaRepository<ArticleEntity, Long>, JpaSpecificationExecutor<ArticleEntity> {}
