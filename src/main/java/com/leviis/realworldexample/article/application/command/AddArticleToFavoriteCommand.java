package com.leviis.realworldexample.article.application.command;

import com.leviis.realworldexample.article.domain.Slug;
import com.leviis.realworldexample.user.domain.User;
import lombok.Builder;

@Builder(setterPrefix = "set")
public record AddArticleToFavoriteCommand(User authenticatedUser, Slug articleSlug) {}
