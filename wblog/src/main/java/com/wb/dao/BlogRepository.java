package com.wb.dao;

import com.wb.pojo.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * JpaSpecificationExecutor<T> : 实现了动态的组合查询
 */
public interface BlogRepository extends JpaRepository<Blog, Integer>, JpaSpecificationExecutor<Blog> {
    /* 根据博客的更新时间查询最新推荐数据 */
    @Query("select b from Blog b where b.recommend = true")
    List<Blog> findTop(Pageable pageable);

    /* 根据博客标题或者博客内容或者博客描述信息进行 GBQ分页搜索查询 */
    @Query("select b from Blog b where b.title like ?1 or b.description like ?1 or b.content like ?1")
    Page<Blog> findByQuery(String query, Pageable pageable);

    @Transactional
    @Modifying
    @Query("update Blog b set b.views = b.views+1 where b.id = ?1")
    int updateViews(Integer id);

    @Query("select function('date_format',b.updateTime,'%Y') as year " +
            "from Blog b group by function('date_format',b.updateTime,'%Y') order by year desc ")
    List<String> findGroupYear();

    @Query("select b from Blog b where function('date_format',b.updateTime,'%Y') = ?1")
    List<Blog> findByYear(String year);
}
