package com.leviis.realworldexample.tag.adapter.outbound.persistence.tag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaTagRepository extends JpaRepository<TagEntity, Long> {}
