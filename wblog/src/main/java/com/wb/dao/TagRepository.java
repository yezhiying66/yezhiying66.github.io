package com.wb.dao;

import com.wb.pojo.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Banyue on 2022/3/18 0018 14:29
 */
public interface TagRepository extends JpaRepository<Tag, Integer> {

    Tag findByName(String name);

    /* 根据博客 list.size 大小查询标签数据 */
    @Query("select t from Tag t")
    List<Tag> findTop(Pageable pageable);

}
