package com.leviis.realworldexample.tag.adapter.outbound.persistence.tag;

import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaTagRepository extends JpaRepository<TagEntity, Long> {
    List<TagEntity> findAllByNameIn(Set<String> tagNames);
}
