package com.leviis.realworldexample.article.application.query.handler;

import com.leviis.realworldexample.article.application.port.outbound.ArticleQueryRepository;
import com.leviis.realworldexample.article.application.port.outbound.UserFavoriteArticleQueryRepository;
import com.leviis.realworldexample.article.application.query.FindAllFeedArticleQuery;
import com.leviis.realworldexample.article.application.readmodel.ArticleWithAuthor;
import com.leviis.realworldexample.article.domain.Article;
import com.leviis.realworldexample.tag.application.port.outbound.TagQueryRepository;
import com.leviis.realworldexample.tag.domain.Tag;
import com.leviis.realworldexample.user.application.port.outbound.FollowQueryRepository;
import com.leviis.realworldexample.user.application.port.outbound.UserQueryRepository;
import com.leviis.realworldexample.user.domain.Email;
import com.leviis.realworldexample.user.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindAllFeedArticleHandlerTest {
    @Mock
    private ArticleQueryRepository articleQueryRepository;

    @Mock
    private FollowQueryRepository followQueryRepository;

    @Mock
    private TagQueryRepository tagQueryRepository;

    @Mock
    private UserFavoriteArticleQueryRepository userFavoriteArticleQueryRepository;

    @Mock
    private UserQueryRepository userQueryRepository;

    @InjectMocks
    private FindAllFeedArticleHandler findAllFeedArticleHandler;

    @Test
    public void execute_positiveCase_returnListOfArticleWithAuthor() {
        Tag tag1 = Tag.builder().setId(1L).setName("tag1").build();
        Tag tag2 = Tag.builder().setId(2L).setName("tag2").build();
        Tag tag3 = Tag.builder().setId(3L).setName("tag3").build();
        User author1 = User.builder()
                .setId(2L)
                .setEmail(new Email("author1@example.com"))
                .setUsername("author1")
                .build();
        User author2 = User.builder()
                .setId(3L)
                .setEmail(new Email("author2@example.com"))
                .setUsername("author2")
                .build();
        Article article1 = Article.builder()
                .setId(1L)
                .setTitle("article 1")
                .setAuthorId(author1.id())
                .setTagIds(List.of(tag1.id(), tag2.id()))
                .build();
        Article article2 = Article.builder()
                .setId(2L)
                .setTitle("article 2")
                .setAuthorId(author2.id())
                .setTagIds(List.of(tag2.id(), tag3.id()))
                .build();
        when(followQueryRepository.findAllFollowingIdByFollowerId(anyLong()))
                .thenReturn(List.of(author1.id(), author2.id()));
        when(articleQueryRepository.findAllByAuthorIdIn(anyList(), anyInt(), anyInt()))
                .thenReturn(List.of(article1, article2));
        when(tagQueryRepository.findAllByIdIn(anySet())).thenReturn(List.of(tag1, tag2, tag3));
        when(userFavoriteArticleQueryRepository.findUserArticleFavoriteIn(any(User.class), anyList()))
                .thenReturn(List.of(article1.id()));
        when(userFavoriteArticleQueryRepository.getFavoriteCount(anyList()))
                .thenReturn(Map.of(
                        article1.id(), 10L,
                        article2.id(), 20L));
        when(userQueryRepository.findByIds(anySet())).thenReturn(List.of(author1, author2));
        when(userQueryRepository.findIsFollowingIn(any(User.class), anyList())).thenReturn(List.of(author1.id()));

        User user = User.builder()
                .setId(1L)
                .setEmail(new Email("user@example.com"))
                .setUsername("user")
                .build();
        int limit = 10;
        int offset = 0;
        FindAllFeedArticleQuery query = FindAllFeedArticleQuery.builder()
                .setUser(user)
                .setLimit(limit)
                .setOffset(offset)
                .build();
        List<ArticleWithAuthor> response = findAllFeedArticleHandler.execute(query);

        assertEquals(2, response.size());

        ArticleWithAuthor response1 = response.getFirst();
        assertEquals(article1.title(), response1.title());
        assertEquals(2, response1.tags().size());
        assertEquals(tag1.name(), response1.tags().getFirst());
        assertEquals(tag2.name(), response1.tags().get(1));
        assertTrue(response1.isFavorite());
        assertEquals(10L, response1.favoriteCount());
        assertEquals(author1.username(), response1.author().username());
        assertTrue(response1.author().isFollowing());

        ArticleWithAuthor response2 = response.get(1);
        assertEquals(article2.title(), response2.title());
        assertEquals(2, response2.tags().size());
        assertEquals(tag2.name(), response2.tags().getFirst());
        assertEquals(tag3.name(), response2.tags().get(1));
        assertFalse(response2.isFavorite());
        assertEquals(20L, response2.favoriteCount());
        assertEquals(author2.username(), response2.author().username());
        assertFalse(response2.author().isFollowing());
    }
}
