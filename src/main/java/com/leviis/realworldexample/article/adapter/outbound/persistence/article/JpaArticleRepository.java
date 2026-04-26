package com.leviis.realworldexample.article.adapter.outbound.persistence.article;

import com.leviis.realworldexample.user.adapter.outbound.persistence.user.UserEntity;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaArticleRepository
        extends JpaRepository<ArticleEntity, Long>, JpaSpecificationExecutor<ArticleEntity> {
    Page<ArticleEntity> findAllByAuthorIn(List<UserEntity> authors, Pageable pageable);
}
