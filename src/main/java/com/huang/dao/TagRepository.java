package com.huang.dao;

import com.huang.po.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author huangneng
 * @create 2020-04-17 14:30
 */
public interface TagRepository extends JpaRepository<Tag, Long> {

    Tag findByName(String name);

    @Query("select  t from  Tag  t")
    List<Tag> findTop(Pageable pageable);
}
