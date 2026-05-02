package com.leviis.realworldexample.article.application.command.handler;

import com.leviis.realworldexample.article.application.command.DeleteArticleCommand;
import com.leviis.realworldexample.article.application.port.inbound.DeleteArticleUseCase;
import com.leviis.realworldexample.article.application.port.outbound.ArticleCommandRepository;
import org.apache.logging.log4j.internal.annotation.SuppressFBWarnings;

public final class DeleteArticleHandler implements DeleteArticleUseCase {
    private final ArticleCommandRepository articleCommandRepository;

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification = "Repository interfaces are effectively immutable — no internal state is exposed")
    public DeleteArticleHandler(final ArticleCommandRepository articleCommandRepository) {
        this.articleCommandRepository = articleCommandRepository;
    }

    @Override
    public void execute(final DeleteArticleCommand command) {
        articleCommandRepository.deleteByAuthorAndSlug(command.author(), command.slug());
    }
}
