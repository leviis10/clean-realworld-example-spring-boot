package com.leviis.realworldexample.article.application.command.handler;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.leviis.realworldexample.article.application.command.CreateArticleCommand;
import com.leviis.realworldexample.article.application.port.outbound.ArticleCommandRepository;
import com.leviis.realworldexample.article.application.readmodel.ArticleWithBodyAndAuthor;
import com.leviis.realworldexample.article.domain.Article;
import com.leviis.realworldexample.mock.MockArticle;
import com.leviis.realworldexample.mock.MockAuthor;
import com.leviis.realworldexample.mock.MockTag;
import com.leviis.realworldexample.tag.application.port.outbound.TagQueryRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CreateArticleHandlerTest {
    @Mock
    private TagQueryRepository tagQueryRepository;

    @Mock
    private ArticleCommandRepository articleCommandRepository;

    @InjectMocks
    private CreateArticleHandler createArticleHandler;

    @Test
    public void execute_positiveCase_returnArticleWithBodyAndAuthor() {
        when(tagQueryRepository.findAllByNameIn(anySet())).thenReturn(List.of(MockTag.TAG_1, MockTag.TAG_2));
        when(articleCommandRepository.create(any(Article.class), anyMap())).thenReturn(MockArticle.ARTICLE_DOMAIN_1);

        List<String> tags = MockArticle.ARTICLE_1_TAG_IDS.stream()
                .map(tagId -> MockTag.TAG_MAP.get(tagId).name())
                .toList();
        CreateArticleCommand command = CreateArticleCommand.builder()
                .setTitle(MockArticle.ARTICLE_1_TITLE)
                .setDescription(MockArticle.ARTICLE_1_DESCRIPTION)
                .setBody(MockArticle.ARTICLE_1_BODY)
                .setTags(tags)
                .setAuthor(MockAuthor.AUTHOR_MAP.get(MockArticle.ARTICLE_1_AUTHOR_ID))
                .build();
        ArticleWithBodyAndAuthor response = createArticleHandler.execute(command);

        assertEquals(MockArticle.ARTICLE_DOMAIN_1.slug().toString(), response.slug());
        assertEquals(MockArticle.ARTICLE_DOMAIN_1.title(), response.title());
        assertEquals(MockArticle.ARTICLE_DOMAIN_1.description(), response.description());
        assertEquals(MockArticle.ARTICLE_DOMAIN_1.body(), response.body());
        assertTrue(response.tags().stream().anyMatch(tag -> MockArticle.ARTICLE_1_TAG_IDS.stream()
                .map(tagId -> MockTag.TAG_MAP.get(tagId).name())
                .toList()
                .contains(tag)));
        assertEquals(MockArticle.ARTICLE_DOMAIN_1.createdAt(), response.createdAt());
        assertEquals(MockArticle.ARTICLE_DOMAIN_1.updatedAt(), response.updatedAt());
        assertFalse(response.isFavorite());
        assertEquals(0, response.favoritesCount());
        assertEquals(
                MockAuthor.AUTHOR_MAP
                        .get(MockArticle.ARTICLE_DOMAIN_1.authorId())
                        .username(),
                response.author().username());
    }
}
