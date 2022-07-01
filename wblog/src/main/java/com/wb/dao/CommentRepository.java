package com.wb.dao;

import com.wb.pojo.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Banyue on 2022/3/18 0018 14:29
 */
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findByBlogIdAndParentCommentNull(Integer blogId, Sort sort);

}
