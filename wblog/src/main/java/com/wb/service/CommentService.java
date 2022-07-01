package com.wb.service;

import com.wb.pojo.Comment;

import java.util.List;


public interface CommentService {

    List<Comment> listCommentByBlogId(Integer blogId);

    Comment saveComment(Comment comment);
}
