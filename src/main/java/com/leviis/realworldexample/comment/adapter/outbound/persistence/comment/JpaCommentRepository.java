package com.leviis.realworldexample.comment.adapter.outbound.persistence.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaCommentRepository extends JpaRepository<CommentEntity, Long> {}
