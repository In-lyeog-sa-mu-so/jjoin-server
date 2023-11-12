package org.dongguk.jjoin.repository;

import org.dongguk.jjoin.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    @Query("SELECT t FROM Tag t WHERE t.name IN :names")
    List<Tag> findByNames(List<String> names);

    @Query("SELECT t FROM Tag AS t ORDER BY t.name")
    List<Tag> findAllSort();
}
