package com.leviis.realworldexample.article.application.command;

import com.leviis.realworldexample.article.domain.Slug;
import com.leviis.realworldexample.user.domain.User;

public record DeleteArticleCommand(User author, Slug slug) {}
