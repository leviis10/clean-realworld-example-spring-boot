package com.leviis.realworldexample.article.application.port.outbound;

import com.leviis.realworldexample.article.domain.Article;
import com.leviis.realworldexample.tag.domain.Tag;
import java.util.Map;

@FunctionalInterface
public interface ArticleCommandRepository {
    Article create(Article article, Map<Long, Tag> tagMap);
}
