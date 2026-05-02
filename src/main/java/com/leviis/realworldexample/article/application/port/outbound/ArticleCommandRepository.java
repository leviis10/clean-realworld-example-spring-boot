package com.leviis.realworldexample.article.application.port.outbound;

import com.leviis.realworldexample.article.domain.Article;
import com.leviis.realworldexample.article.domain.Slug;
import com.leviis.realworldexample.tag.domain.Tag;
import com.leviis.realworldexample.user.domain.User;
import java.util.Map;

public interface ArticleCommandRepository {
    Article create(Article article, Map<Long, Tag> tagMap);

    Article save(Article article);

    void deleteByAuthorAndSlug(User author, Slug slug);
}
