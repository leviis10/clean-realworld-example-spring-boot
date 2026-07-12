package com.leviis.realworldexample.article.application.query.handler;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.leviis.realworldexample.article.application.port.outbound.ArticleQueryRepository;
import com.leviis.realworldexample.article.application.port.outbound.UserFavoriteArticleQueryRepository;
import com.leviis.realworldexample.article.application.query.FindAllArticleQuery;
import com.leviis.realworldexample.article.application.readmodel.ArticleWithAuthor;
import com.leviis.realworldexample.article.domain.Article;
import com.leviis.realworldexample.article.domain.Slug;
import com.leviis.realworldexample.tag.application.port.outbound.TagQueryRepository;
import com.leviis.realworldexample.tag.domain.Tag;
import com.leviis.realworldexample.user.application.port.outbound.UserQueryRepository;
import com.leviis.realworldexample.user.domain.Email;
import com.leviis.realworldexample.user.domain.User;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FindAllArticleHandlerTest {
    @Mock
    private ArticleQueryRepository articleQueryRepository;

    @Mock
    private TagQueryRepository tagQueryRepository;

    @Mock
    private UserQueryRepository userQueryRepository;

    @Mock
    private UserFavoriteArticleQueryRepository userFavoriteArticleQueryRepository;

    @InjectMocks
    private FindAllArticleHandler findAllArticleHandler;

    @Test
    public void execute_positiveCase_returnListOfArticleWithAuthor() {
        Tag tag1 = Tag.builder().setId(1L).setName("tag1").build();
        Tag tag2 = Tag.builder().setId(2L).setName("tag2").build();
        Tag tag3 = Tag.builder().setId(3L).setName("tag3").build();
        User author1 = User.builder()
                .setId(1L)
                .setEmail(new Email("author1@example.com"))
                .setUsername("author1")
                .build();
        User author2 = User.builder()
                .setId(2L)
                .setEmail(new Email("author2@example.com"))
                .setUsername("author2")
                .build();
        Slug article1Slug = new Slug("article-1", UUID.randomUUID());
        Slug article2Slug = new Slug("article-2", UUID.randomUUID());
        String article1Title = "article 1";
        String article2Title = "article 2";
        Article article1 = Article.builder()
                .setId(1L)
                .setTitle(article1Title)
                .setTagIds(List.of(tag1.id(), tag2.id()))
                .setAuthorId(author1.id())
                .setSlug(article1Slug)
                .build();
        Article article2 = Article.builder()
                .setId(2L)
                .setTitle(article2Title)
                .setTagIds(List.of(tag2.id(), tag3.id()))
                .setAuthorId(author2.id())
                .setSlug(article2Slug)
                .build();
        Map<Long, Long> favoriteCountMap = Map.of(
                article1.id(), 10L,
                article2.id(), 20L);
        when(articleQueryRepository.findAll(anyString(), anyString(), anyString(), anyInt(), anyInt()))
                .thenReturn(List.of(article1, article2));
        when(tagQueryRepository.findAllByIdIn(anySet())).thenReturn(List.of(tag1, tag2, tag3));
        when(userFavoriteArticleQueryRepository.findUserArticleFavoriteIn(any(User.class), anyList()))
                .thenReturn(List.of(article1.id()));
        when(userFavoriteArticleQueryRepository.getFavoriteCount(anyList())).thenReturn(favoriteCountMap);
        when(userQueryRepository.findByIds(anySet())).thenReturn(List.of(author1, author2));
        when(userQueryRepository.findIsFollowingIn(any(User.class), anyList())).thenReturn(List.of(author1.id()));

        User user = User.builder()
                .setEmail(new Email("user@example.com"))
                .setUsername("user")
                .build();
        String tag = "tag-query";
        String author = "author-query";
        String favoriteBy = "favorite-by-query";
        FindAllArticleQuery query = FindAllArticleQuery.builder()
                .setUser(user)
                .setTag(tag)
                .setAuthor(author)
                .setFavoriteBy(favoriteBy)
                .build();
        List<ArticleWithAuthor> response = findAllArticleHandler.execute(query);

        assertEquals(2, response.size());

        ArticleWithAuthor articleResponse1 = response.getFirst();
        assertEquals(article1Slug.toString(), articleResponse1.slug());
        assertEquals(article1Title, articleResponse1.title());
        assertEquals(2, articleResponse1.tags().size());
        assertEquals(2, articleResponse1.tags().size());
        assertEquals(tag1.name(), articleResponse1.tags().getFirst());
        assertEquals(tag2.name(), articleResponse1.tags().get(1));
        assertTrue(articleResponse1.isFavorite());
        assertEquals(favoriteCountMap.get(article1.id()), articleResponse1.favoriteCount());
        assertEquals(author1.username(), articleResponse1.author().username());
        assertTrue(articleResponse1.author().isFollowing());

        ArticleWithAuthor articleResponse2 = response.get(1);
        assertEquals(article2Slug.toString(), articleResponse2.slug());
        assertEquals(article2Title, articleResponse2.title());
        assertEquals(2, articleResponse2.tags().size());
        assertEquals(2, articleResponse2.tags().size());
        assertEquals(tag2.name(), articleResponse2.tags().getFirst());
        assertEquals(tag3.name(), articleResponse2.tags().get(1));
        assertFalse(articleResponse2.isFavorite());
        assertEquals(favoriteCountMap.get(article2.id()), articleResponse2.favoriteCount());
        assertEquals(author2.username(), articleResponse2.author().username());
        assertFalse(articleResponse2.author().isFollowing());
    }

    @Test
    public void execute_queryIsNull_throwNullPointerException() {
        assertThrows(NullPointerException.class, () -> findAllArticleHandler.execute(null));
    }
}
