package com.leviis.realworldexample.article.application.query.handler;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.leviis.realworldexample.article.application.exceptions.ArticleNotFoundException;
import com.leviis.realworldexample.article.application.port.outbound.ArticleQueryRepository;
import com.leviis.realworldexample.article.application.port.outbound.UserFavoriteArticleQueryRepository;
import com.leviis.realworldexample.article.application.query.GetArticleQuery;
import com.leviis.realworldexample.article.application.readmodel.ArticleWithBodyAndAuthor;
import com.leviis.realworldexample.article.domain.Article;
import com.leviis.realworldexample.article.domain.Slug;
import com.leviis.realworldexample.tag.application.port.outbound.TagQueryRepository;
import com.leviis.realworldexample.tag.domain.Tag;
import com.leviis.realworldexample.user.application.port.outbound.FollowQueryRepository;
import com.leviis.realworldexample.user.application.port.outbound.UserQueryRepository;
import com.leviis.realworldexample.user.domain.Email;
import com.leviis.realworldexample.user.domain.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetArticleHandlerTest {
    @Mock
    private ArticleQueryRepository articleQueryRepository;

    @Mock
    private TagQueryRepository tagQueryRepository;

    @Mock
    private UserFavoriteArticleQueryRepository userFavoriteArticleQueryRepository;

    @Mock
    private UserQueryRepository userQueryRepository;

    @Mock
    private FollowQueryRepository followQueryRepository;

    @InjectMocks
    private GetArticleHandler getArticleHandler;

    @Test
    public void execute_positiveCase_returnArticleWithBodyAndAuthor() {
        Slug slug = new Slug("article-slug", UUID.randomUUID());
        Tag tag1 = Tag.builder().setId(1L).setName("tag1").build();
        Tag tag2 = Tag.builder().setId(2L).setName("tag2").build();
        User author = User.builder()
                .setId(2L)
                .setEmail(new Email("author@example.com"))
                .setUsername("author")
                .build();
        Article article = Article.builder()
                .setId(1L)
                .setTitle("Article Title")
                .setSlug(slug)
                .setTagIds(List.of(tag1.id(), tag2.id()))
                .setAuthorId(author.id())
                .build();
        when(articleQueryRepository.getBySlug(any(Slug.class))).thenReturn(Optional.of(article));
        when(tagQueryRepository.findAllByIdIn(anySet())).thenReturn(List.of(tag1, tag2));
        when(userFavoriteArticleQueryRepository.getIsFavoriteArticle(any(User.class), anyLong()))
                .thenReturn(true);
        when(userFavoriteArticleQueryRepository.getFavoriteCount(any(Article.class)))
                .thenReturn(10L);
        when(userQueryRepository.findById(anyLong())).thenReturn(Optional.of(author));
        when(followQueryRepository.findIsFollowing(any(User.class), any(User.class)))
                .thenReturn(true);

        User authenticatedUser = User.builder()
                .setId(1L)
                .setUsername("authenticatedUser")
                .setEmail(new Email("authenticateduser@example.com"))
                .build();
        GetArticleQuery query = GetArticleQuery.builder()
                .setAuthenticatedUser(authenticatedUser)
                .setSlug(slug.value())
                .setSlugId(slug.id())
                .build();
        ArticleWithBodyAndAuthor response = getArticleHandler.execute(query);

        assertEquals(slug.toString(), response.slug());
        assertEquals(article.title(), response.title());
        assertEquals(2, response.tags().size());
        assertEquals(tag1.name(), response.tags().getFirst());
        assertEquals(tag2.name(), response.tags().getLast());
        assertTrue(response.isFavorite());
        assertEquals(10L, response.favoritesCount());
        assertEquals(author.username(), response.author().username());
        assertTrue(response.author().isFollowing());
    }

    @Test
    public void execute_articleNotFound_throwArticleNotFoundException() {
        Slug slug = new Slug("article-slug", UUID.randomUUID());
        when(articleQueryRepository.getBySlug(any(Slug.class))).thenThrow(ArticleNotFoundException.class);

        User authenticatedUser = User.builder()
                .setId(1L)
                .setUsername("authenticatedUser")
                .setEmail(new Email("authenticateduser@example.com"))
                .build();
        GetArticleQuery query = GetArticleQuery.builder()
                .setAuthenticatedUser(authenticatedUser)
                .setSlug(slug.value())
                .setSlugId(slug.id())
                .build();

        assertThrows(ArticleNotFoundException.class, () -> getArticleHandler.execute(query));
    }

    @Test
    public void execute_authenticatedUserIsNull_returnArticleWithBodyAndAuthor() {
        Slug slug = new Slug("article-slug", UUID.randomUUID());
        Tag tag1 = Tag.builder().setId(1L).setName("tag1").build();
        Tag tag2 = Tag.builder().setId(2L).setName("tag2").build();
        User author = User.builder()
                .setId(2L)
                .setEmail(new Email("author@example.com"))
                .setUsername("author")
                .build();
        Article article = Article.builder()
                .setId(1L)
                .setTitle("Article Title")
                .setSlug(slug)
                .setTagIds(List.of(tag1.id(), tag2.id()))
                .setAuthorId(author.id())
                .build();
        when(articleQueryRepository.getBySlug(any(Slug.class))).thenReturn(Optional.of(article));
        when(tagQueryRepository.findAllByIdIn(anySet())).thenReturn(List.of(tag1, tag2));
        when(userFavoriteArticleQueryRepository.getIsFavoriteArticle(eq(null), anyLong()))
                .thenReturn(false);
        when(userFavoriteArticleQueryRepository.getFavoriteCount(any(Article.class)))
                .thenReturn(10L);
        when(userQueryRepository.findById(anyLong())).thenReturn(Optional.of(author));
        when(followQueryRepository.findIsFollowing(eq(null), any(User.class))).thenReturn(false);

        GetArticleQuery query = GetArticleQuery.builder()
                .setAuthenticatedUser(null)
                .setSlug(slug.value())
                .setSlugId(slug.id())
                .build();
        ArticleWithBodyAndAuthor response = getArticleHandler.execute(query);

        assertEquals(slug.toString(), response.slug());
        assertEquals(article.title(), response.title());
        assertEquals(2, response.tags().size());
        assertEquals(tag1.name(), response.tags().getFirst());
        assertEquals(tag2.name(), response.tags().getLast());
        assertFalse(response.isFavorite());
        assertEquals(10L, response.favoritesCount());
        assertEquals(author.username(), response.author().username());
        assertFalse(response.author().isFollowing());
    }

    @Test
    public void execute_queryIsNull_throwNullPointerException() {
        assertThrows(NullPointerException.class, () -> getArticleHandler.execute(null));
    }
}
