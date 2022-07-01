package com.wb.dao;

import com.wb.pojo.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Banyue on 2022/3/18 0018 14:29
 */
public interface TypeRepository extends JpaRepository<Type, Integer> {

    Type findByName(String name);

    /* 根据博客 list.size 大小查询分类数据 */
    @Query("select t from Type t") // 自定义查询
    List<Type> findTop(Pageable pageable);
}
