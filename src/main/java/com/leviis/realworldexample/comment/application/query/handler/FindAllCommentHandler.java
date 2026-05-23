package com.leviis.realworldexample.comment.application.query.handler;

import com.leviis.realworldexample.comment.application.port.inbound.FindAllCommentUseCase;
import com.leviis.realworldexample.comment.application.port.outbound.CommentQueryRepository;
import com.leviis.realworldexample.comment.application.query.FindAllCommentQuery;
import com.leviis.realworldexample.comment.application.readmodel.CommentWithAuthor;
import com.leviis.realworldexample.comment.domain.Comment;
import com.leviis.realworldexample.user.application.port.outbound.FollowQueryRepository;
import com.leviis.realworldexample.user.application.port.outbound.UserQueryRepository;
import com.leviis.realworldexample.user.domain.User;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class FindAllCommentHandler implements FindAllCommentUseCase {
    private final CommentQueryRepository commentQueryRepository;
    private final UserQueryRepository userQueryRepository;
    private final FollowQueryRepository followQueryRepository;

    @Override
    public List<CommentWithAuthor> execute(final FindAllCommentQuery query) {
        final List<Comment> foundComments = commentQueryRepository.findAllByArticleSlug(query.articleSlug());

        final Set<Long> authorIds =
                foundComments.stream().map(Comment::authorId).collect(Collectors.toSet());

        final List<User> foundAuthors = userQueryRepository.findByIds(authorIds);
        final Optional<List<Long>> foundFollowings = Optional.ofNullable(query.user())
                .map(user -> followQueryRepository.findByFollowerAndFollowingIdIn(user.id(), authorIds));

        return CommentWithAuthor.from(foundComments, foundAuthors, foundFollowings.orElse(null));
    }
}
