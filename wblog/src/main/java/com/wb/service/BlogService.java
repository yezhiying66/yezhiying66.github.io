package com.wb.service;

import com.wb.pojo.Blog;
import com.wb.pojo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;


public interface BlogService {

    Blog getBlog(Integer id);

    Blog getAndConvert(Integer id);

    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);

    Page<Blog> listBlog(Pageable pageable);

    Page<Blog> listSearchBlog(String query, Pageable pageable);

    Page<Blog> listBlog(Integer tagId, Pageable pageable);

    List<Blog> listRecommentBlogTop(Integer size);

    Map<String, List<Blog>> archivesBlog();

    Long countBlog();

    Blog saveBlog(Blog blog);

    Blog updateBlog(Integer id, Blog blog);

    void deleteBlog(Integer id);
}
